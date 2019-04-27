package com.choxsu.front.article;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
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

    public void index() {
       renderError(404);
    }

    @Before(AddClickInterceptor.class)
    public void detail() {
        Blog record = articleService.findBlog(getParaToInt());
        if (record != null) {
            setAttr("blog", record);
            render("detail.html");
        } else {
            renderError(404);
        }
    }

    @ActionKey("/feed")
    public void feed() {
        renderXml("feed.xml");
    }
}
