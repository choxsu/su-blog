

package com.choxsu.common.interceptor;

import com.choxsu.common.entity.Account;
import com.choxsu.common.kit.IpKit;
import com.choxsu.login.LoginService;
import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 从 cookie 中获取 sessionId，如果获取到则根据该值使用 LoginService
 * 得到登录的 Account 对象 ---> loginAccount，供后续的流程使用
 * 
 * 注意：将此拦截器设置为全局拦截器，所有 action 都需要
 */
public class LoginSessionInterceptor implements Interceptor {

    public static final String remindKey = "_remind";

    //@Inject
	LoginService loginService = Enhancer.enhance(LoginService.class);


	public void intercept(Invocation inv) {
        Account loginAccount ;
		Controller c = inv.getController();
		String sessionId = c.getCookie(LoginService.sessionIdName);
		if (sessionId != null) {
			loginAccount = loginService.getLoginAccountWithSessionId(sessionId);
			if (loginAccount == null) {
				String loginIp = IpKit.getRealIp(c.getRequest());
				loginAccount = loginService.loginWithSessionId(sessionId, loginIp);
			}
			if (loginAccount != null) {
				// 用户登录账号
				c.setAttr(LoginService.loginAccountCacheName, loginAccount);
			} else {
				c.removeCookie(LoginService.sessionIdName); // cookie 登录未成功，证明该 cookie 已经没有用处，删之
			}
		}

		inv.invoke();

      /*  if (loginAccount != null) {
            // remind 对象用于生成提醒 tips
            Remind remind = RemindService.me.getRemind(loginAccount.getId());
            if (remind != null) {
                if (remind.getReferMe() > 0 || remind.getMessage() > 0 || remind.getFans() > 0) {
                    c.setAttr(remindKey, remind);
                }
            }
        }*/
	}
}



