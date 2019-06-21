

package com.choxsu._admin.account;

import com.choxsu.utils.kit.SensitiveWordsKit;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;

/**
 * AccountUpdateValidator 验证账号修改功能表单
 */
public class AccountSaveUpdateValidator extends Validator {


    protected void validate(Controller c) {
        setShortCircuit(true);
        String actionMethodName = getActionMethodName();

        /**
         * 验证 nickName
         */
        if (SensitiveWordsKit.checkSensitiveWord(c.getPara("account.nickName")) != null) {
            addError("msg", "昵称不能包含敏感词");
        }
        validateRequired("account.nickName", "msg", "昵称不能为空");
        validateString("account.nickName", 1, 19, "msg", "昵称不能超过19个字");

        String nickName = c.getPara("account.nickName").trim();
        Ret ret = validateNickName(nickName);
        if (ret.isFail()) {
            addError("msg", ret.getStr("msg"));
        }

        if (actionMethodName.equalsIgnoreCase("save")) {
            /**
             * 验证 userName
             */
            validateRequired("account.userName", "msg", "邮箱不能为空");
            String para = c.getPara("account.userName");
            validateEmail("account.userName", "msg", "邮箱格式不正确");
            //验证密码
            validateRequired("account.password", "msg", "密码不能为空");
            validateString("account.password", 6, 16, "msg", "密码长度6到16位之间");

        }

    }

    protected void handleError(Controller c) {
        c.setAttr("state", "fail");
        c.renderJson();
    }


    /**
     * TODO 用正则来匹配这些不能使用的字符，而不是用这种 for + contains 这么土的办法
     * 初始化的时候仍然用这个数组，然后用 StringBuilder 来个 for 循环拼成如下的形式：
     * regex = "( |`|~|!|......|\(|\)|=|\[|\]|\?|<|>\。|\,)"
     * 直接在数组中添加转义字符
     * <p>
     * TODO 找时间将所有 nickName 的校验全部封装起来，供 Validattor 与 RegService 中重用，目前先只补下缺失的校验
     * TODO RegService 中的 nickName 校验也要重用同一份代码，以免代码重复
     */
    public static Ret validateNickName(String nickName) {
        if (StrKit.isBlank(nickName)) {
            return Ret.fail("msg", "昵称不能为空");
        }

        if (nickName.contains("@") || nickName.contains("＠")) { // 全角半角都要判断
            return Ret.fail("msg", "昵称不能包含 \"@\" 字符");
        }
        if (nickName.contains(" ") || nickName.contains("　")) {
            return Ret.fail("msg", "昵称不能包含空格");
        }

        // 放开了 _-.  三个字符的限制
        String[] arr = {" ", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "=", "+",
                "[", "]", "{", "}", "\\", "|", ";", ":", "'", "\"", ",", "<", ">", "/", "?",
                "　", "＠", "＃", "＆", "，", "。", "《", "》", "？"};   // 全角字符
        for (String s : arr) {
            if (nickName.contains(s)) {
                return Ret.fail("msg", "昵称不能包含字符: \"" + s + "\"");
            }
        }

        return Ret.ok();
    }
}

