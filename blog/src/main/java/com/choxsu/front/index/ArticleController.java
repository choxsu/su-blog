package com.choxsu.front.index;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.pageview.AddClickInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 */
@Before(ArticleSEO.class)
public class ArticleController extends BaseController {

    @Inject
    ArticleService articleService;

    @ActionKey("/")
    public void redirectToArticle() {
        redirect("/article");
    }

    public void index() {
        int page = getParaToInt("p", 1);
        Page<Record> blogPage = articleService.findArticles(page, 10, null);
        setAttr("page", blogPage);
        render("blog/index.html");
    }

    @Before(AddClickInterceptor.class)
    public void detail() {
        Record record = articleService.findBlog(getParaToInt());
        if (record != null) {
            setAttr("blog", record);
            render("blog/detail.html");
        } else {
            renderError(404);
        }
    }

    @ActionKey("/feed")
    public void feed() {
        renderXml("feed.xml");
    }
}
