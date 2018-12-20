
package com.choxsu._admin.login;

import com.choxsu.common.entity.Account;
import com.choxsu.kit.EmailKit;
import com.choxsu.kit.IpKit;
import com.choxsu.kit.RSAKit;
import com.choxsu.common.render.MyCaptchaRender;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

/**
 * 登录控制器
 */
public class LoginController extends Controller {

    @Inject
    LoginService srv;

    /**
     * 显示登录界面
     */
    public void index() {
        keepPara("returnUrl");  // 保持住 returnUrl 这个参数，以便在登录成功后跳转到该参数指向的页面
        render("index.html");
    }

    /**
     * 登录
     */
    @Before(LoginValidator.class)
    public void doLogin() {
        String pModel = PropKit.get("hexModulus");
        String exp = PropKit.get("hexPrivateExponent");
        String encript = getPara("encryptPwd");
        RSAPrivateKey privateKey = RSAKit.getRSAPrivateKey(pModel, exp);
        String password = RSAKit.decryptString(privateKey, encript);
        boolean keepLogin = getParaToBoolean("keepLogin", false);
        String loginIp = IpKit.getRealIp(getRequest());
        Ret ret = srv.login(getPara("userName"), password, keepLogin, loginIp);
        if (ret.isOk()) {
            Account account = (Account) ret.get(LoginService.loginAccountCacheName);
            String content = "在 " + new Date() + "登陆 Choxsu博客社区后台成功 <br/> 登陆ip:" + IpKit.getRealIp(this.getRequest()) + "<br/> 如果非本人登录，请及时联系超级管理员";
            try {
                EmailKit.sendEmail(account.getUserName(), "登陆styg.site后台成功提示", content, true);
            } catch (Exception e) {
                LogKit.error(e.getMessage(), e);
            }
            String sessionId = ret.getStr(LoginService.sessionIdName);
            int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
            setCookie(LoginService.sessionIdName, sessionId, maxAgeInSeconds, true);
            setAttr(LoginService.loginAccountCacheName, ret.get(LoginService.loginAccountCacheName));
            ret.set("returnUrl", getPara("returnUrl", "/admin"));    // 如果 returnUrl 存在则跳过去，否则跳去首页
        }
        renderJson(ret);
    }

    /**
     * 退出登录
     */
    @Clear
    @ActionKey("/logout")
    public void logout() {
        srv.logout(getCookie(LoginService.sessionIdName));
        removeCookie(LoginService.sessionIdName);
        redirect("/login");
    }

    /**
     * 显示忘记密码页面
     */
    public void forgetPassword() {
        render("forget_password.html");
    }

    /**
     * 发送找回密码邮件
     */
    public void sendRetrievePasswordEmail() {
        Ret ret = srv.sendRetrievePasswordAuthEmail(getPara("email"));
        renderJson(ret);
    }

    /**
     * 1：keepPara("authCode") 将密码找回链接中问号挂参的 authCode 传递到页面
     * 2：在密码找回页面中与用户输入的新密码一起传回给 doPassReturn 进行密码修改
     */
    public void retrievePassword() {
        keepPara("authCode");
        render("retrieve_password.html");
    }

    /**
     * ajax 密码找回
     * 1：判断 authCode 是否有效
     * 2：有效则更新密码
     */
    public void doRetrievePassword() {
        Ret ret = srv.retrievePassword(getPara("authCode"), getPara("newPassword"));
        renderJson(ret);
    }

    /**
     * 获取验证码
     */
    public void captcha() {
        render(new MyCaptchaRender());
    }
}

