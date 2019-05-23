

package com.choxsu._front.route;

import com.choxsu._front.article.ArticleController;
import com.choxsu._front.index.IndexController;
import com.choxsu._front.login.LoginController;
import com.choxsu._front.my.MyController;
import com.choxsu._front.register.RegisterController;
import com.choxsu._front.tags.TagsController;
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
        add("/login", LoginController.class, "/login");
        add("/register", RegisterController.class, "/register");
        add("/article", ArticleController.class, "/blog");
        add("/article/tag", TagsController.class, "/blog");

        /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓个人中心↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
        add("/my", MyController.class, "/my");
        /*↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑个人中心↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
    }

}
