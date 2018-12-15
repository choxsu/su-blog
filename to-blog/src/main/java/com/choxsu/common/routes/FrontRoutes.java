

package com.choxsu.common.routes;

import com.choxsu.common.interceptor.TagListInterceptor;
import com.choxsu.web.front.index.IndexController;
import com.jfinal.config.Routes;

/**
 * 前台路由
 *
 * @author choxsu
 */
public class FrontRoutes extends Routes {

    @Override
    public void config() {
        addInterceptor(new TagListInterceptor());

        setBaseViewPath("/_view");
        add("/", IndexController.class, "/");
    }

}
