

package com.choxsu.common.routes;

import com.choxsu.web.front.index.ArticleController;
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
        add("/", ArticleController.class, "/");
        add("/tag", TagsController.class, "/");
    }

}
