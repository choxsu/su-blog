

package com.choxsu.front.login;

import com.choxsu.common.render.MyCaptchaRender;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * ajax 登录参数验证
 */
public class LoginValidator extends Validator {

	protected void validate(Controller c) {
		setShortCircuit(true);

		validateRequired("userName", "userNameMsg", "邮箱不能为空");
		validateEmail("userName", "userNameMsg", "邮箱格式不正确");
		validateRequired("encryptPwd", "passwordMsg", "密码key不能为空");
		boolean captcha = CaptchaRender.validate(c.getCookie(MyCaptchaRender.captchaNameDefault), c.get("captcha"));
		if (!captcha){
			addError("captchaMsg", "验证码不正确");
		}
	}

	protected void handleError(Controller c) {
		c.renderJson();
	}
}
