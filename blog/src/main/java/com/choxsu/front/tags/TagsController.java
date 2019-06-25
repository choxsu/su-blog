package com.choxsu.front.tags;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;

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
        Page<Blog> page = tagsService.findBlogByTagId(pageNumber, 10, tagId);
        setAttr("page", page);
        render("index.html");
    }
}
