package com.choxsu._admin.blog;

import com.choxsu._admin.blog.tag.AdminTagService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogCategory;
import com.choxsu.common.entity.BlogTag;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogController extends BaseController {

    AdminBlogService adminBlogService = AdminBlogService.me;

    AdminTagService tagService = AdminTagService.me;

    public void index() {

        Page<Blog> blogPage = adminBlogService.paginate(getParaToInt("p", 1));
        setAttr("blogPage", blogPage);
        render("index.html");
    }

    public void add() {
        commonInfo();
        render("addOrEdit.html");
    }

    private void commonInfo() {
        CategoryEnum[] values = CategoryEnum.values();
        List<BlogCategory> list = new ArrayList<>();
        BlogCategory blogCategory;
        for (CategoryEnum value : values) {
            blogCategory = new BlogCategory();
            blogCategory.setName(value.getName());
            list.add(blogCategory);
        }
        setAttr("categoryList", list);
        setAttr("tagList", tagService.findAll());
    }

    @Before(BlogValid.class)
    public void save() {
        Blog blog = getModel(Blog.class, "blog");
        blog.setAccountId(getLoginAccountId());
        blog.setUpdateAt(new Date());
        blog.setCreateAt(new Date());
        blog.save();
        renderJson(Ret.ok());
    }

    public void edit() {
        commonInfo();
        setAttr("blog", adminBlogService.DAO.findById(getParaToInt("id")));
        render("addOrEdit.html");
    }

    @Before(BlogValid.class)
    public void update() {
        Blog blog = getModel(Blog.class, "blog");
        String html = getPara("md-html-code");
        blog.setContent(html);
        blog.setAccountId(getLoginAccountId());
        blog.setCreateAt(new Date());
        blog.update();
        renderJson(Ret.ok());
    }

    public void delete() {
        Integer id = getParaToInt("id");
        adminBlogService.DAO.deleteById(id);
        renderJson(Ret.ok().set("msg", "删除成功！"));
    }

}
