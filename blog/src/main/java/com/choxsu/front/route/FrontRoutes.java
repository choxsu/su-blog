

package com.choxsu.front.route;

import com.choxsu.front.article.ArticleController;
import com.choxsu.front.index.IndexController;
import com.choxsu.front.login.LoginController;
import com.choxsu.front.my.MyController;
import com.choxsu.front.register.RegisterController;
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
        setBaseViewPath("/view");

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
