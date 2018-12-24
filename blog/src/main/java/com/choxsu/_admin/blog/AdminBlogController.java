package com.choxsu._admin.blog;

import com.choxsu._admin.tag.AdminTagService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogCategory;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogController extends BaseController {

    public static final String MARKED_ID = "markedContent";

    @Inject
    AdminBlogService adminBlogService;
    @Inject
    AdminTagService tagService;

    public void index() {
        Account loginAccount = getLoginAccount();
        Integer p = getParaToInt("p", 1);
        Integer size = getParaToInt("size", 10);
        Page<Blog> blogPage = adminBlogService.list(p, size, loginAccount);
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
        Blog blog = getParaToSet();
        Ret ret = adminBlogService.saveOrUpdateArticle(blog);
        renderJson(ret);
    }

    @NotAction
    private Blog getParaToSet() {
        Blog blog = getModel(Blog.class, "blog");
        String html = getPara(MARKED_ID + "-html-code");
        String md = getPara(MARKED_ID + "-markdown-doc");
        blog.setContent(html);
        blog.setAccountId(getLoginAccountId());
        blog.setMarkedContent(md);
        return blog;
    }

    public void edit() {
        keepPara("p");
        commonInfo();
        setAttr("blog", adminBlogService.DAO.findById(getParaToInt("id")));
        render("addOrEdit.html");
    }

    @Before(BlogValid.class)
    public void update() {
        Blog blog = getParaToSet();
        Ret ret = adminBlogService.saveOrUpdateArticle(blog);
        renderJson(ret);
    }

    public void delete() {
        Integer id = getParaToInt("id");
        adminBlogService.DAO.deleteById(id);
        renderJson(Ret.ok().set("msg", "删除成功！"));
    }

}
