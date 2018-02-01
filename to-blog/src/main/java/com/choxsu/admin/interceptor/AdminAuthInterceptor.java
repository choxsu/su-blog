package com.choxsu.admin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * @author choxsu
 */
public class AdminAuthInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {

        inv.invoke();
    }
}
