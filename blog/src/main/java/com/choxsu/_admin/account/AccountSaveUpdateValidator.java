/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * <p>
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * <p>
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * <p>
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.choxsu._admin.account;

import com.choxsu.kit.SensitiveWordsKit;
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
        if (nickName.contains("@") || nickName.contains("＠")) { // 全角半角都要判断
            addError("msg", "昵称不能包含 \"@\" 字符");
        }
        if (nickName.contains(" ") || nickName.contains("　")) {
            addError("msg", "昵称不能包含空格");
        }
        Ret ret = AccountSaveUpdateValidator.validateNickName(nickName);
        if (ret.isFail()) {
            addError("msg", ret.getStr("msg"));
        }

        /**
         * 验证 userName
         */
        validateRequired("account.userName", "msg", "邮箱不能为空");
        String para = c.getPara("account.userName");
        System.out.println(para);
        validateEmail("account.userName", "msg", "邮箱格式不正确");
        if (actionMethodName.equalsIgnoreCase("save")) {
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

