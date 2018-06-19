package com.choxsu.common.interceptor;

import com.choxsu.common.entity.Visitor;
import com.choxsu.common.kit.IpKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author choxsu
 */
public class VisitorInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        //将用户访问信息保存到数据库
        Controller c = inv.getController();
        HttpServletRequest request = c.getRequest();
        String actionKey = inv.getActionKey();

        //过滤后台地址的访问记录
        if (actionKey.indexOf("/admin") == 0){
            inv.invoke();
            return;
        }

        String url = request.getRequestURL().toString();
        String ip = IpKit.getRealIp(request);
        String method = request.getMethod();
        String userAgent = request.getHeader("user-agent");
        LOGGER.info("请求ip:{}", ip);

        Visitor visitor = new Visitor();
        visitor.setIp(ip);
        visitor.setUrl(url);
        visitor.setMethod(method);
        visitor.setClient(userAgent);
        visitor.setRequestTime(new Date());
        boolean b = visitor.save();
        LOGGER.info("保存后返回的结果：{}", b);
        inv.invoke();
    }

}
