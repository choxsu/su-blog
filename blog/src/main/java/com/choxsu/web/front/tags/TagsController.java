package com.choxsu.web.front.tags;

import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:44
 */
@Before(TagsSEO.class)
public class TagsController extends BaseController {

    @Inject
    TagsService tagsService;

    public void index() {
        Integer tagId = getParaToInt();
        Integer pageNumber = getParaToInt("p", 1);
        Page<Record> page = tagsService.findBlogByTagId(pageNumber, 10, tagId);
        setAttr("page", page);
        render("blog/index.html");
    }
}
