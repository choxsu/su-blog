package com.choxsu._admin.tag;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.BlogTag;
import com.choxsu.front.index.ArticleService;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author chox su
 * @date 2018/6/12 22:09
 */
public class AdminTagController extends BaseController {

    @Inject
    AdminTagService tagService;
    @Inject
    ArticleService articleService;

    public void index(){
        Page<BlogTag> blogTagPage = tagService.paginate(getParaToInt("p", 1), 10);
        setAttr("blogTagPage", blogTagPage);
        render("index.html");
    }

    public void add() {
        render("add.html");
    }

    @Before(AdminTagValid.class)
    public void save() {
        BlogTag tag = getModel(BlogTag.class, "tag");
        tag.setStatus(0);
        tag.save();
        renderJson(Ret.ok());
    }

    public void edit() {
        keepPara("p");
        setAttr("tag", tagService.DAO.findById(getParaToInt("id")));
        render("edit.html");
    }

    @Before(AdminTagValid.class)
    public void update() {
        BlogTag tag = getModel(BlogTag.class, "tag");
        tag.update();
        renderJson(Ret.ok());
    }

    public void delete() {
        Integer id = getParaToInt("id");
        tagService.DAO.deleteById(id);
        renderJson(Ret.ok().set("msg", "删除成功！"));
    }


}