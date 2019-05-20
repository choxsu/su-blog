package com.choxsu._admin.tag;

import com.choxsu._admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.BlogTag;
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

    @Remark("标签管理首页")
    public void index(){
        Page<BlogTag> blogTagPage = tagService.paginate(getParaToInt("p", 1), 10);
        setAttr("blogTagPage", blogTagPage);
        render("index.html");
    }

    @Remark("标签添加页面")
    public void add() {
        render("add.html");
    }

    @Remark("标签保存")
    @Before(AdminTagValid.class)
    public void save() {
        BlogTag tag = getModel(BlogTag.class, "tag");
        tag.setStatus(0);
        tag.save();
        renderJson(Ret.ok());
    }

    @Remark("标签编辑页面")
    public void edit() {
        keepPara("p");
        setAttr("tag", tagService.DAO.findById(getParaToInt("id")));
        render("edit.html");
    }

    @Remark("标签更新")
    @Before(AdminTagValid.class)
    public void update() {
        BlogTag tag = getModel(BlogTag.class, "tag");
        tag.update();
        renderJson(Ret.ok());
    }

    @Remark("标签删除")
    public void delete() {
        Integer id = getParaToInt("id");
        tagService.DAO.deleteById(id);
        renderJson(Ret.ok().set("msg", "删除成功！"));
    }

}
