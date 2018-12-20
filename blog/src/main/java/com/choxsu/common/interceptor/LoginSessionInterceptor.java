

package com.choxsu.common.interceptor;

import com.choxsu._admin.login.AdminLoginService;
import com.choxsu.common.entity.Account;
import com.choxsu.kit.IpKit;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 从 cookie 中获取 sessionId，如果获取到则根据该值使用 AdminLoginService
 * 得到登录的 Account 对象 ---> loginAccount，供后续的流程使用
 * 
 * 注意：将此拦截器设置为全局拦截器，所有 action 都需要
 */
public class LoginSessionInterceptor implements Interceptor {

    public static final String remindKey = "_remind";

    @Inject
	AdminLoginService adminLoginService;


	public void intercept(Invocation inv) {
        Account loginAccount ;
		Controller c = inv.getController();
		String sessionId = c.getCookie(AdminLoginService.sessionIdName);
		if (sessionId != null) {
			loginAccount = adminLoginService.getLoginAccountWithSessionId(sessionId);
			if (loginAccount == null) {
				String loginIp = IpKit.getRealIp(c.getRequest());
				loginAccount = adminLoginService.loginWithSessionId(sessionId, loginIp);
			}
			if (loginAccount != null) {
				// 用户登录账号
				c.setAttr(AdminLoginService.loginAccountCacheName, loginAccount);
			} else {
				c.removeCookie(AdminLoginService.sessionIdName); // cookie 登录未成功，证明该 cookie 已经没有用处，删之
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



