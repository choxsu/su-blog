package com.choxsu.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpServletResponse;

/**
 * @author choxsu
 * @date 2018/8/24 0024
 */
public class CORSInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
        HttpServletResponse response = inv.getController().getResponse();
        response.addHeader("Access-Control-Allow-Origin", "*");
    }
}
