package com.choxsu.front.register;

import com.choxsu.admin.account.AccountSaveUpdateValidator;
import com.choxsu.utils.kit.SensitiveWordsKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

/**
 * @author choxsu
 */
public class RegisterValidator extends Validator {
    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);
        validateRequired("nickname", "msg", "昵称不能为空");
        /**
         * 验证 nickName
         */
        if (SensitiveWordsKit.checkSensitiveWord(c.getPara("nickname")) != null) {
            addError("msg", "昵称不能包含敏感词");
        }
        validateString("nickname", 1, 16, "msg", "昵称不能超过16个字");
        String nickname = c.getPara("nickname").trim();
        Ret ret = AccountSaveUpdateValidator.validateNickName(nickname);
        if (ret.isFail()) {
            addError("msg", ret.getStr("msg"));
        }

        validateRequired("userName", "msg", "邮箱不能为空");
        validateEmail("userName", "msg", "邮箱格式不正确");
        validateRequired("password", "msg", "密码不能为空");
        validateRequired("passwordAgain", "msg", "密码不能为空");
        validateEqualField("password", "passwordAgain", "msg", "两次密码不相等");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
