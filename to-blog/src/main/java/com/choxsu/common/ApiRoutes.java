package com.choxsu.common;

import com.choxsu.api.home.HomeController;
import com.jfinal.config.Routes;

/**
 * @author chox su
 * @date 2018/03/02 10:23
 */
public class ApiRoutes extends Routes {

    @Override
    public void config() {

        add("/api/home", HomeController.class);


    }
}
