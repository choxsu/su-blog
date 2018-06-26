

package com.choxsu.common.routes;

import com.choxsu.login.LoginController;
import com.choxsu.reg.RegController;
import com.choxsu.web.front.about.AboutController;
import com.choxsu.web.front.blog.BlogController;
import com.choxsu.web.front.code.CodeController;
import com.choxsu.common.interceptor.TagListInterceptor;
import com.choxsu.web.front.favorite.FavoriteController;
import com.choxsu.web.front.index.IndexController;
import com.choxsu.web.front.search.SearchController;
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
        addInterceptor(new TagListInterceptor());

        setBaseViewPath("/view");

        add("/", IndexController.class, "/");
        add("/blog", BlogController.class, "/blog");

        add("/favorite", FavoriteController.class, "/favorite");
        add("/code", CodeController.class, "/code");
        add("/about", AboutController.class, "/about");

        add("/tag", TagsController.class, "/tags");
        add("/search", SearchController.class, "/search");

        accountRoutes();
    }

    private void accountRoutes() {
        add("/login", LoginController.class, "/login");
        add("/reg", RegController.class, "/reg");
    }

    public static void main(String[] args) {
        int a = 2;
        int b = a--;
        //++a  先做a+1再赋值,  比如a = 2 ; ++a , a=3; ++a=3
        //a++  先赋值后再a+1,  比如a = 2 ; a++ , a=2; a++=3
        //--a  先做a-1再赋值,  比如a = 2 ; --a , a=1; --a=1
        //a--  先赋值和再a-1,  比如a = 2 ; a-- , a=2; a--=1
        System.out.println(b);
        System.out.println(a);
        if (++a > 10){
            System.out.println(a++);
        }else {
            String s = String.format("%d,%d", a--, a--);
            System.out.println(s);
        }
        System.out.println(a);
    }
}
