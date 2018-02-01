package com.choxsu.index;

import com.choxsu.common.BaseController;
import com.choxsu.common.interceptor.TagListInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

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
