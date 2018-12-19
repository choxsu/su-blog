package com.choxsu.common.pageview;

import com.choxsu.web.front.index.ArticleService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class AddClickInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        inv.invoke();

        Controller c = inv.getController();

        if (c.isParaExists(0)) {
            Integer id = c.getParaToInt();
            ArticleService.addClick(id);
        }
    }
}
