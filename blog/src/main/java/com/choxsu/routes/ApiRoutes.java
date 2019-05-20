package com.choxsu.routes;

import com.choxsu._api.blog.ApiBlogController;
import com.jfinal.config.Routes;

/**
 * 接口路由
 *
 * @author chox su
 * @date 2018/03/02 10:23
 */
public class ApiRoutes extends Routes {

    @Override
    public void config() {
        add("/_api/v1/blog", ApiBlogController.class);
    }
}
