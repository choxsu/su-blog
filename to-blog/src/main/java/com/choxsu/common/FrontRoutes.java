/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * <p>
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * <p>
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * <p>
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.choxsu.common;

import com.choxsu.about.AboutController;
import com.choxsu.blog.BlogController;
import com.choxsu.code.CodeController;
import com.choxsu.common.interceptor.TagListInterceptor;
import com.choxsu.favorite.FavoriteController;
import com.choxsu.index.IndexController;
import com.choxsu.search.SearchController;
import com.choxsu.tags.TagsController;
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
