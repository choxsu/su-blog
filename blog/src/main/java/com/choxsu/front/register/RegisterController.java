
package com.choxsu.front.register;

import com.choxsu.kit.IpKit;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Ret;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 登录控制器
 */
public class RegisterController extends Controller {

    @Inject
    RegisterService registerService;

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 到注册页面
     */
    public void index(){
        render("index.html");
    }

    /**
     * 注册业务
     * @param regModel
     */
    @Before(RegisterValidator.class)
    public void doRegister(@Para("") RegEntity regModel){
        boolean validateCaptcha = validateCaptcha("captcha");
        if (!validateCaptcha) {
            renderJson(Ret.fail("msg", "验证码输入不正确！"));
            return;
        }
        renderJson(registerService.register(regModel, IpKit.getRealIp(getRequest())));
    }


}

