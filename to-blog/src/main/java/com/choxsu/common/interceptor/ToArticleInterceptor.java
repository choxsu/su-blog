package com.choxsu.common.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class ToArticleInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller c = invocation.getController();
        String controllerKey = invocation.getControllerKey();
        if (controllerKey.equals("/")){
            c.redirect("/article");
        }else {
            invocation.invoke();
        }
    }
}
