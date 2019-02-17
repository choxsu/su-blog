

package com.choxsu.routes;

import com.choxsu.front.article.ArticleController;
import com.choxsu.front.index.IndexController;
import com.choxsu.front.tags.TagsController;
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
        add("/article", ArticleController.class, "/blog");
        add("/article/tag", TagsController.class, "/blog");
    }

}
