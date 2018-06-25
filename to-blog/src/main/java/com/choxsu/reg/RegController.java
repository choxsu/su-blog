

package com.choxsu.reg;

import com.choxsu.common.interceptor.TagListInterceptor;
import com.choxsu.common.kit.IpKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;

/**
 * 注册控制器
 */
@Clear(TagListInterceptor.class)
public class RegController extends Controller {

	RegService srv = RegService.me;

	public void index() {
		render("index.html");
	}

	/**
	 * 注册操作
 	 */
	@Before(RegValidator.class)
	public void save() {
		String ip = IpKit.getRealIp(getRequest());
		Ret ret = srv.reg(getPara("userName"), getPara("password"), getPara("nickName"), ip);
		if (ret.isOk()) {
			ret.set("regEmail", getPara("userName"));
		}
		renderJson(ret);
	}

	/**
	 * 显示还没激活页面
	 */
	public void notActivated() {
		render("not_activated.html");
	}

	/**
	 * 重发激活邮件
	 */
	public void reSendActivateEmail() {
		Ret ret = srv.reSendActivateEmail(getPara("email"));
		renderJson(ret);
	}

	/**
	 * 激活，发送给用户注册邮箱中的带有 authCode 的激活链接指向该 action
	 */
	public void activate() {
		Ret ret = srv.activate(getPara("authCode"));
		setAttr("ret", ret);
		render("activate.html");
	}

	public void captcha() {
		renderCaptcha();
	}
}
