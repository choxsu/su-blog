

package com.choxsu.common.kit;

import com.choxsu.common.handler.DruidFilterHandler;
import com.jfinal.handler.Handler;
import com.jfinal.plugin.druid.DruidStatViewHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 创建 DruidStatViewHandler 的工具类
 * <p>
 * 可通过 "/blog/druid" 访问到 druid 提供的 sql 监控与统计功能
 * 方便找到慢 sql，进而对慢 sql 进行优化
 * 注意：这里的访问路径是下面代码中指定的，可以设置为任意路径
 * <p>
 * 注意 druid 监控模块中使用的静态资源文件如 .html .css 被打包在了 druid 的jar 包之中
 * 如果你的项目在前端有 nginx 代理过了这些静态资源，需要将这些资源解压出来并放到
 * 正确的目录下面
 * <p>
 * 具体到该配置中的 url 为 "/blog/druid"，那么相关静态资源需要解压到该目录之下
 *
 * @author choxsu
 */
public class DruidKit {

    public static DruidStatViewHandler getDruidStatViewHandler() {

        return new DruidStatViewHandler("/blog/druid", request -> {
            //TODO 这里需要去做判断，管理员才可以登录查看
            return false;
        });
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie cookie = getCookieObject(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 获取Cookie
     *
     * @param request
     * @param name
     * @return
     */
    private static Cookie getCookieObject(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * 获取过滤字段
     * @param regex
     * @return
     */
    public static Handler getFilterHandler(String regex) {

        return new DruidFilterHandler(regex);
    }
}
