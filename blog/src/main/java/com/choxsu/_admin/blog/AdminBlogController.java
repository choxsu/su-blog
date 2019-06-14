package com.choxsu._admin.blog;

import com.choxsu._admin.permission.Remark;
import com.choxsu._admin.tag.AdminTagService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogCategory;
import com.choxsu.common.redis.RedisKey;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Redis;

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

    @Remark("文章首页")
    public void index() {
        Account loginAccount = getLoginAccount();
        Integer p = getParaToInt("p", 1);
        Integer size = getParaToInt("size", 10);
        Page<Blog> blogPage = adminBlogService.list(p, size, loginAccount);
        setAttr("blogPage", blogPage);
        render("index.html");
    }

    @Remark("文章添加页面")
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
        //setAttr("categoryList", list);
        setAttr("tagList", tagService.findAll());
    }

    @Remark("文章保存")
    @Before(BlogValid.class)
    public void save() {
        Blog blog = getParaToSet();
        Ret ret = adminBlogService.saveOrUpdateArticle(blog);
        renderJson(ret);
    }

    private Blog getParaToSet() {
        Blog blog = getModel(Blog.class, "blog");
        String html = getPara(MARKED_ID + "-html-code");
        String md = getPara(MARKED_ID + "-markdown-doc");
        blog.setContent(html);
        blog.setAccountId(getLoginAccountId());
        blog.setMarkedContent(md);
        return blog;
    }

    @Remark("文章编辑页面")
    public void edit() {
        keepPara("p");
        commonInfo();
        setAttr("blog", adminBlogService.findById(getParaToInt("id")));
        render("addOrEdit.html");
    }

    @Remark("文章更新")
    @Before(BlogValid.class)
    public void update() {
        Blog blog = getParaToSet();
        Ret ret = adminBlogService.saveOrUpdateArticle(blog);
        renderJson(ret);
    }

    @Remark("文章删除")
    public void delete() {
        Integer id = getParaToInt("id");
        adminBlogService.deleteById(id);
        renderJson(Ret.ok().set("msg", "删除成功！"));
    }

    @Remark("开启评论")
    public void allowComments(Integer id){
        Ret ret = adminBlogService.allowComments(id);
        renderJson(ret);
    }


    @Remark("关闭评论")
    public void unAllowComments(Integer id){
        Ret ret = adminBlogService.unAllowComments(id);
        renderJson(ret);
    }

    @Remark("一键开启评论")
    public void oneKeyAllowComments(){
        Ret ret = adminBlogService.oneKeyAllowComments();
        renderJson(ret);
    }

}
