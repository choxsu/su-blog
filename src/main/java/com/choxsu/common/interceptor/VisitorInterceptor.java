package com.choxsu.common.interceptor;

import com.choxsu.common.kit.IpKit;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author choxsu
 */
public class VisitorInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        //将用户访问信息保存到数据库
        Controller c = inv.getController();
        HttpServletRequest request = c.getRequest();
        System.out.println(request.getRequestURL().toString());
        String url = request.getRequestURL().toString();
        String ip = IpKit.getRealIp(request);
        String method = request.getMethod();
        String userAgent = request.getHeader("user-agent");
        Record record = new Record();
        record.set("ip", ip);
        record.set("url", url);
        record.set("method",  method);
        record.set("client", userAgent);
        record.set("requestTime", new Date());
        Db.save("visitor", record);
        inv.invoke();
    }

}
