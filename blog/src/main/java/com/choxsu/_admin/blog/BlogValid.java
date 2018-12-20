package com.choxsu._admin.blog;

import com.choxsu.kit.SensitiveWordsKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * @author choxsu
 */
public class BlogValid extends Validator {

    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);

        if (SensitiveWordsKit.checkSensitiveWord(c.getPara("blog.title")) != null) {
            addError("msg", "标题不能包含敏感词");
        }

        validateRequired("blog.title", "msg", "标题不能为空");
        validateString("blog.title", 1, 60, "msg", "标题不能超过60个字");

        validateRequired("blog.category", "msg", "请选择分类");
        validateRequired("blog.tag_id", "msg", "请选择标签");

        validateString("blog.markedContent", 10, 100000,  "msg", "内容最少10个字");
        validateString("md-html-code", 10, 500000,  "msg", "内容最少10个字");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
