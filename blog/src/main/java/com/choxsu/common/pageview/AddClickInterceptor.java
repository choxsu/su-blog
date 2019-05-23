package com.choxsu.common.pageview;

import com.choxsu.utils.kit.IpKit;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AddClickInterceptor implements Interceptor {

    @Inject
    PageViewService pageViewService;

    @Override
    public void intercept(Invocation inv) {
        inv.invoke();

        Controller c = inv.getController();

        if (c.isParaExists(0)) {
            Integer id = c.getParaToInt();
            String ip = IpKit.getRealIp(c.getRequest());
            pageViewService.processArticleToCache(id, ip);
        }
    }
}
