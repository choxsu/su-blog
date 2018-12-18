package com.choxsu.web.front.index;

import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 */
public class ArticleController extends BaseController {

    @Inject
    ArticleService articleService;

    public void index() {
        int page = getParaToInt("p", 1);
        Page<Record> blogPage = articleService.findArticles(page, 5, null);
        setAttr("page", blogPage);
        render("blog/index.html");
    }

    public void detail() {
        Integer id = getParaToInt();
        Record record = articleService.findBlog(id);
        setAttr("blog", record);
        render("blog/detail.html");
    }
}
