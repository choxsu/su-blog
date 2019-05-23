package com.choxsu._admin.tag;

import com.choxsu.utils.kit.SensitiveWordsKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * @author chox su
 * @date 2018/6/13 22:13
 */
public class AdminTagValid extends Validator {
    @Override
    protected void validate(Controller controller) {

        setShortCircuit(true);

        if (SensitiveWordsKit.checkSensitiveWord(controller.getPara("tag.name")) != null) {
            addError("msg", "标签名称不能包含敏感词");
        }

        validateRequired("tag.name", "msg", "标签名称不能为空");
        validateString("tag.name", 1, 60, "msg", "标签名称不能超过60个字");
    }

    @Override
    protected void handleError(Controller controller) {
        controller.renderJson();
    }
}
