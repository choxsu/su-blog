package com.choxsu.index;

import com.choxsu.common.BaseController;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @author choxsu
 */
public class IndexController extends BaseController {

    private static final IndexService indexService = Enhancer.enhance(IndexService.class);

    public void index(){
        List<Record> tags = indexService.findBlogTags();
        setAttr("tags", tags);
        render("index.html");
    }
}
