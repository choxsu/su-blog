package com.choxsu.common.pageview;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * @author choxsu
 */
public class VisitorInterceptor implements Interceptor {


    @Inject
    PageViewService pageViewService;

    @Override
    public void intercept(Invocation inv) {
        String actionKey = inv.getActionKey();

        //过滤后台地址的访问记录
        if (actionKey.indexOf("/admin") == 0) {
            inv.invoke();
            return;
        }
        //先写如缓存
        toCache(inv.getController());
        inv.invoke();
    }

    private void toCache(Controller c) {
        //将用户访问信息保存到数据库
        pageViewService.processPageView(c);
    }

}
