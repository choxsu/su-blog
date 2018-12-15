package com.choxsu.web.front.index;

import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 */
public class IndexController extends BaseController {

    @Inject
    IndexService indexService;


    public void index() {

        int page = getParaToInt("p", 1);

        Page<Record> blogPage = indexService.findBlogs(page);
        setAttr("blogs", blogPage);

        render("blog/index.html");
    }

    public void detail() {

        String id = getPara("id");

        Record record = indexService.findBlog(id);
        setAttr("blog", record);

        render("blog/detail.html");
    }
}
