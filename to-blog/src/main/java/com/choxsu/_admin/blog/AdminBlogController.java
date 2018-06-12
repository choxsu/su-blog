package com.choxsu._admin.blog;

import com.choxsu._admin.blog.category.AdminCategoryService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogCategory;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogController extends BaseController {

    AdminBlogService adminBlogService = AdminBlogService.me;

    AdminCategoryService categoryService = AdminCategoryService.me;

    public void index() {

        Page<Blog> blogPage = adminBlogService.paginate(getParaToInt("p", 1));
        setAttr("blogPage", blogPage);
        render("index.html");
    }

    public void add() {
        setAttr("categoryList", categoryService.findAll(BlogCategory.tableName));
        render("add.html");
    }

    public void save() {

    }

    public void edit() {
        render("edit.html");
    }

    public void update() {

    }

    public void delete() {

    }

}
