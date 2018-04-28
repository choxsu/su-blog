package com.choxsu.web.front.index;

import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 */
public class IndexController extends BaseController {

    private static final IndexService indexService = Enhancer.enhance(IndexService.class);

    public void index() {
        int page;

        String p = getPara(0);

        try {
            page = Integer.parseInt(p);
        }catch (Exception e){
            page = 1;
        }

        Page<Record> blogPage = indexService.findBlogs(page);
        setAttr("blogs", blogPage);

        render("index.html");
    }
}
