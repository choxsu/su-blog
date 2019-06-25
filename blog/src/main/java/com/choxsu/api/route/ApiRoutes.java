package com.choxsu.api.route;

import com.choxsu.api.blog.ApiBlogController;
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
        add("/api/v1/blog", ApiBlogController.class);
    }
}
