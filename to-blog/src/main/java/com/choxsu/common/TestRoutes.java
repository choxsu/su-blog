package com.choxsu.common;

import com.choxsu.test.TestController;
import com.jfinal.config.Routes;

/**
 * @author chox su
 * @date 2018/03/09 10:08
 */
public class TestRoutes extends Routes {
    @Override
    public void config() {
        add("/test/api/v1", TestController.class);

    }
}
