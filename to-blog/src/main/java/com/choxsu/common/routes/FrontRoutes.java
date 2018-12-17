

package com.choxsu.common.routes;

import com.choxsu.web.front.index.IndexController;
import com.choxsu.web.front.tags.TagsController;
import com.jfinal.config.Routes;

/**
 * 前台路由
 *
 * @author choxsu
 */
public class FrontRoutes extends Routes {

    @Override
    public void config() {
        setBaseViewPath("/_view");
        add("/", IndexController.class, "/");
        add("/tag", TagsController.class, "/");
    }

}
