package com.choxsu.blog;

import com.choxsu.common.BaseController;
import com.choxsu.common.constant.CategoryEnum;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 12:31
 */
public class BlogController extends BaseController {

    private static final BlogService BLOG_SERVICE = Enhancer.enhance(BlogService.class);

    public void index() {
        Integer pageNumber = getParaToInt(0, 1);
        Page<Record> recordPage = BLOG_SERVICE.findBlogListByCategory(pageNumber, 15, CategoryEnum.BLOG.getName());
        setAttr("blogs", recordPage);
        render("list.html");
    }

    public void detail() {
        Integer id = getParaToInt();
        Record record = BLOG_SERVICE.findBlog(id);
        setAttr("blog", record);
        render("detail.html");
    }


}
