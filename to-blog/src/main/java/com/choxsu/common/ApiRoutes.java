package com.choxsu.common;

import com.choxsu.api.blog.ApiBlogController;
import com.choxsu.api.home.ApiHomeController;
import com.jfinal.config.Routes;

/**
 * 接口路由
 * @author chox su
 * @date 2018/03/02 10:23
 */
public class ApiRoutes extends Routes {

    @Override
    public void config() {

        add("/api/v1/home", ApiHomeController.class);
        add("/api/v1/blog", ApiBlogController.class);


    }
}
