
package com.choxsu.front.login;

import com.choxsu.common.entity.Account;
import com.choxsu.common.render.MyCaptchaRender;
import com.choxsu.front.login.entity.QQVo;
import com.choxsu.kit.EmailKit;
import com.choxsu.kit.IpKit;
import com.choxsu.kit.RSAKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.*;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 登录控制器
 */
public class LoginController extends Controller {

    @Inject
    LoginService srv;


    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 显示登录界面
     */
    public void index() {
        keepPara("returnUrl");  // 保持住 returnUrl 这个参数，以便在登录成功后跳转到该参数指向的页面
        render("index.html");
    }

    /**
     * 打开qq登陆页面
     */
    public void qqLogin() {
        redirect(srv.getAuthUrl());
    }

    /**
     * qq登陆回调 http://styg.site/login/qqCallback
     * code=11BBC527B8885683FA4F6AD3F4D9713B&state=ok
     */
    public void qqCallback() {
        Ret ret = srv.qqCallback(get("code"), IpKit.getRealIp(getRequest()));
        if (ret.isOk()) {
            setLoginInfo(ret);
            redirect(getPara("returnUrl", "/"));
            return;
        }
        renderHtml("qq登陆授权失败，<a href=\"/login/qqLogin\">请重试</a>");
    }

    private void setLoginInfo(Ret ret) {
        String sessionId = ret.getStr(LoginService.sessionIdName);
        int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
        setCookie(LoginService.sessionIdName, sessionId, maxAgeInSeconds, true);
        setAttr(LoginService.loginAccountCacheName, ret.get(LoginService.loginAccountCacheName));
    }

    /**
     * 登录
     */
    @Before(LoginValidator.class)
    public void doLogin() {
        String encript = getPara("encryptPwd");
        RSAPrivateKey privateKey = RSAKit.getRSAPrivateKey(PropKit.get("privateKey"));
        if (privateKey == null) {
            renderJson(Ret.fail().set("msg", "RSA私钥无效或不存在"));
            return;
        }
        String password = RSAKit.decryptString(privateKey, encript);
        boolean keepLogin = getParaToBoolean("keepLogin", false);
        String loginIp = IpKit.getRealIp(getRequest());
        Ret ret = srv.login(getPara("userName"), password, keepLogin, loginIp);
        if (ret.isOk()) {
            Account account = (Account) ret.get(LoginService.loginAccountCacheName);
            String content = "在 " + new Date() + "登陆 Choxsu博客社区后台成功 <br/> 登陆ip:" + IpKit.getRealIp(this.getRequest()) + "<br/> 如果非本人登录，请及时联系超级管理员";
            executorService.execute(() -> {
                try {
                    EmailKit.sendEmail(account.getUserName(), "登陆styg.site后台成功提示", content, true);
                } catch (Exception e) {
                    LogKit.error("登录成功发送邮件失败：" + e.getMessage(), e);
                }
            });
            setLoginInfo(ret);
            ret.set("returnUrl", getPara("returnUrl", "/"));    // 如果 returnUrl 存在则跳过去，否则跳去首页
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
        redirect("/");
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

