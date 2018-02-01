package com.choxsu.tags;

import com.choxsu.common.BaseController;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:44
 */
public class TagsController extends BaseController {

    private static TagsService tagsService = Enhancer.enhance(TagsService.class);

    public void index(){
        Integer tagId = getParaToInt();
        setAttr("tag", tagsService.findById(tagId));
        Integer pageNumber = getParaToInt("p", 1);
        Page<Record> page = tagsService.findBlogByTagId(pageNumber, 15 , tagId);
        setAttr("result", page);
        render("list.html");
    }
}
