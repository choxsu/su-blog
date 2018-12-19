

package com.choxsu._admin.account;

import com.choxsu.common.kit.SensitiveWordsKit;
import com.choxsu.reg.RegValidator;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

/**
 * AccountUpdateValidator 验证账号修改功能表单
 */
public class AccountSaveValidator extends Validator {
	
	protected void validate(Controller c) {
		setShortCircuit(true);

		/**
		 * 验证 nickName
 		 */
		if (SensitiveWordsKit.checkSensitiveWord(c.getPara("nickName")) != null) {
			addError("msg", "昵称不能包含敏感词");
		}
		validateRequired("nickName", "msg", "昵称不能为空");
		validateRequired("password", "msg", "密码不能为空");
		validateString("password", 6, 16, "msg", "密码长度6到16个字符");
		validateString("nickName", 1, 19, "msg", "昵称不能超过19个字");

		String nickName = c.getPara("nickName").trim();
		if (nickName.contains("@") || nickName.contains("＠")) { // 全角半角都要判断
			addError("msg", "昵称不能包含 \"@\" 字符");
		}
		if (nickName.contains(" ") || nickName.contains("　")) {
			addError("msg", "昵称不能包含空格");
		}
		Ret ret = RegValidator.validateNickName(nickName);
		if (ret.isFail()) {
			addError("msg", ret.getStr("msg"));
		}

		/**
		 * 验证 userName
		 */
		validateRequired("userName", "msg", "邮箱不能为空");
		validateEmail("userName", "msg", "邮箱格式不正确");
	}

	protected void handleError(Controller c) {
		c.setAttr("state", "fail");
		c.renderJson();
	}
}

