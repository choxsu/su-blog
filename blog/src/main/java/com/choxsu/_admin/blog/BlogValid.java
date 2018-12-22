package com.choxsu._admin.blog;

import com.choxsu.kit.SensitiveWordsKit;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * @author choxsu
 */
public class BlogValid extends Validator {

    AdminBlogService adminBlogService = Aop.get(AdminBlogService.class);

    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);

        if (SensitiveWordsKit.checkSensitiveWord(c.getPara("blog.title")) != null) {
            addError("msg", "标题不能包含敏感词");
        }

        validateRequired("blog.title", "msg", "标题不能为空");
        validateString("blog.title", 1, 60, "msg", "标题不能超过60个字");
        //判断标题名称是否存在,
        if (adminBlogService.isExistTitle(c.getParaToInt("blog.id"), c.getPara("blog.title"))) {
            addError("msg", "标题已经存在，请更换！");
        }

        //validateRequired("blog.category", "msg", "请选择分类");
        validateRequired("blog.tag_id", "msg", "请选择标签");

        validateString("markedContent-markdown-doc", 5, 500000, "msg", "内容最少5个字符");
        validateString("markedContent-html-code", 5, 1000000, "msg", "内容最少5个字符");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
