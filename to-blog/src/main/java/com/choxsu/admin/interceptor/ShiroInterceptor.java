package com.choxsu.admin.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AnnotationsAuthorizingMethodInterceptor;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author chox su
 * @date 2018/02/02 11:49
 */
public class ShiroInterceptor extends AnnotationsAuthorizingMethodInterceptor implements Interceptor {

    public ShiroInterceptor() {
        getMethodInterceptors();//用来扩展其他注解
    }

    @Override
    public void intercept(final Invocation inv) {
        try {
            invoke(new MethodInvocation() {
                @Override
                public Object proceed() throws Throwable {
                    inv.invoke();
                    return inv.getReturnValue();
                }

                @Override
                public Method getMethod() {
                    return inv.getMethod();
                }

                @Override
                public Object[] getArguments() {
                    return inv.getArgs();
                }

                @Override
                public Object getThis() {
                    return inv.getController();
                }
            });
        } catch (Throwable e) {
            if (e instanceof AuthorizationException) {
                doProcessuUnauthorization(inv.getController());
            }
            LogKit.warn("权限错误:" + e.getMessage(), e);
        }

    }

    /**
     * 未授权处理
     *
     * @param controller controller
     */
    private void doProcessuUnauthorization(Controller controller) {
        controller.renderError(401);
    }
}
