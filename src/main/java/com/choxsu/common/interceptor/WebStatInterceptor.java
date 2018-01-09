package com.choxsu.common.interceptor;

import com.alibaba.druid.filter.stat.StatFilterContext;
import com.alibaba.druid.support.http.AbstractWebStatImpl;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.http.stat.*;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.support.profile.ProfileEntryKey;
import com.alibaba.druid.support.profile.ProfileEntryReqStat;
import com.alibaba.druid.support.profile.Profiler;
import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用于配置Web和Druid数据源之间的管理关联监控统计
 *
 * @author chox su
 * @date 2018/01/09 10:59
 */
public class WebStatInterceptor extends AbstractWebStatImpl implements Interceptor {


    private final static Log LOG = LogFactory.getLog(WebStatFilter.class);

    public final static String PARAM_NAME_PROFILE_ENABLE = "profileEnable";
    public final static String PARAM_NAME_SESSION_STAT_ENABLE = "sessionStatEnable";
    public final static String PARAM_NAME_SESSION_STAT_MAX_COUNT = "sessionStatMaxCount";
    public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    public static final String PARAM_NAME_PRINCIPAL_SESSION_NAME = "principalSessionName";
    public static final String PARAM_NAME_PRINCIPAL_COOKIE_NAME = "principalCookieName";
    public static final String PARAM_NAME_REAL_IP_HEADER = "realIpHeader";

    /**
     * PatternMatcher used in determining which paths to react to for a given request.
     */
    protected PatternMatcher pathMatcher = new ServletPathMatcher();

    private Set<String> excludesPattern;


    public WebStatInterceptor() {
        this(null);
    }

    public WebStatInterceptor(String exclusions) {
        if (exclusions != null && exclusions.trim().length() != 0) {
            excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
        }
    }


    @Override
    public void intercept(Invocation inv) {
        init(inv);

        HttpServletRequest httpRequest = inv.getController().getRequest();
        HttpServletResponse httpResponse = inv.getController().getResponse();
        WebStatFilter.StatHttpServletResponseWrapper responseWrapper = new WebStatFilter.StatHttpServletResponseWrapper(httpResponse);

        String requestURI = getRequestURI(httpRequest);

        if (isExclusion(requestURI)) {
            inv.invoke();
            return;
        }

        long startNano = System.nanoTime();
        long startMillis = System.currentTimeMillis();

        WebRequestStat requestStat = new WebRequestStat(startNano, startMillis);
        WebRequestStat.set(requestStat);

        WebSessionStat sessionStat = getSessionStat(httpRequest);
        webAppStat.beforeInvoke();

        WebURIStat uriStat = webAppStat.getURIStat(requestURI, false);

        if (uriStat == null) {
            int index = requestURI.indexOf(";jsessionid=");
            if (index != -1) {
                requestURI = requestURI.substring(0, index);
                uriStat = webAppStat.getURIStat(requestURI, false);
            }
        }

        if (isProfileEnable()) {
            Profiler.initLocal();
            Profiler.enter(requestURI, Profiler.PROFILE_TYPE_WEB);
        }

        // 第一次访问时，uriStat这里为null，是为了防止404攻击。
        if (uriStat != null) {
            uriStat.beforeInvoke();
        }

        // 第一次访问时，sessionId为null，如果缺省sessionCreate=false，sessionStat就为null。
        if (sessionStat != null) {
            sessionStat.beforeInvoke();
        }

        Throwable error = null;
        try {
            inv.invoke();
        } catch (RuntimeException e) {
            error = e;
            throw e;
        } catch (Error e) {
            error = e;
            throw e;
        } finally {
            long endNano = System.nanoTime();
            requestStat.setEndNano(endNano);

            long nanos = endNano - startNano;
            webAppStat.afterInvoke(error, nanos);

            if (sessionStat == null) {
                sessionStat = getSessionStat(httpRequest);
                if (sessionStat != null) {
                    sessionStat.beforeInvoke(); // 补偿
                }
            }

            if (sessionStat != null) {
                sessionStat.afterInvoke(error, nanos);
                sessionStat.setPrincipal(getPrincipal(httpRequest));
            }

            if (uriStat == null) {
                int status = responseWrapper.getStatus();
                if (status == HttpServletResponse.SC_NOT_FOUND) {
                    String errorUrl = contextPath + "error_" + status;
                    uriStat = webAppStat.getURIStat(errorUrl, true);
                } else {
                    uriStat = webAppStat.getURIStat(requestURI, true);
                }

                if (uriStat != null) {
                    uriStat.beforeInvoke(); // 补偿调用
                }
            }

            if (uriStat != null) {
                uriStat.afterInvoke(error, nanos);
            }

            WebRequestStat.set(null);

            if (isProfileEnable()) {
                Profiler.release(nanos);

                Map<ProfileEntryKey, ProfileEntryReqStat> requestStatsMap = Profiler.getStatsMap();
                if (uriStat != null) {
                    uriStat.getProfiletat().record(requestStatsMap);
                }
                Profiler.removeLocal();
            }
        }
    }

    /**
     * 是否排除
     *
     * @param requestURI
     * @return
     */
    public boolean isExclusion(String requestURI) {
        if (excludesPattern == null || requestURI == null) {
            return false;
        }

        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }

        for (String pattern : excludesPattern) {
            if (pathMatcher.matches(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    private void init(Invocation inv) {
        HttpServletRequest request = inv.getController().getRequest();
        {
            String param = request.getParameter(PARAM_NAME_PRINCIPAL_SESSION_NAME);
            if (param != null) {
                param = param.trim();
                if (param.length() != 0) {
                    this.principalSessionName = param;
                }
            }
        }

        {
            String param = request.getParameter(PARAM_NAME_PRINCIPAL_COOKIE_NAME);
            if (param != null) {
                param = param.trim();
                if (param.length() != 0) {
                    this.principalCookieName = param;
                }
            }
        }

        {
            String param = request.getParameter(PARAM_NAME_SESSION_STAT_ENABLE);
            if (param != null && param.trim().length() != 0) {
                param = param.trim();
                if ("true".equals(param)) {
                    this.sessionStatEnable = true;
                } else if ("false".equals(param)) {
                    this.sessionStatEnable = false;
                } else {
                    LOG.error("WebStatFilter Parameter '" + PARAM_NAME_SESSION_STAT_ENABLE + "' config error");
                }
            }
        }

        {
            String param = request.getParameter(PARAM_NAME_PROFILE_ENABLE);
            if (param != null && param.trim().length() != 0) {
                param = param.trim();
                if ("true".equals(param)) {
                    this.profileEnable = true;
                } else if ("false".equals(param)) {
                    this.profileEnable = false;
                } else {
                    LOG.error("WebStatFilter Parameter '" + PARAM_NAME_PROFILE_ENABLE + "' config error");
                }
            }
        }
        {
            String param = request.getParameter(PARAM_NAME_SESSION_STAT_MAX_COUNT);
            if (param != null && param.trim().length() != 0) {
                param = param.trim();
                try {
                    this.sessionStatMaxCount = Integer.parseInt(param);
                } catch (NumberFormatException e) {
                    LOG.error("WebStatFilter Parameter '" + PARAM_NAME_SESSION_STAT_ENABLE + "' config error", e);
                }
            }
        }

        // realIpHeader
        {
            String param = request.getParameter(PARAM_NAME_REAL_IP_HEADER);
            if (param != null) {
                param = param.trim();
                if (param.length() != 0) {
                    this.realIpHeader = param;
                }
            }
        }

        StatFilterContext.getInstance().addContextListener(statFilterContextListener);

        this.contextPath = DruidWebUtils.getContextPath(request.getServletContext());
        if (webAppStat == null) {
            webAppStat = new WebAppStat(contextPath, this.sessionStatMaxCount);
        }
        WebAppStatManager.getInstance().addWebAppStatSet(webAppStat);
    }

    public void destroy() {
        StatFilterContext.getInstance().removeContextListener(statFilterContextListener);

        if (webAppStat != null) {
            WebAppStatManager.getInstance().remove(webAppStat);
        }
    }
}
