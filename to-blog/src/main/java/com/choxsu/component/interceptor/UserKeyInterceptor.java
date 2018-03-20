package com.choxsu.component.interceptor;

import com.choxsu.component.util.JFlyFoxUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import java.util.UUID;

/**
 * 用户认证拦截器
 * 
 * @author flyfox 2014-2-11
 */
public class UserKeyInterceptor implements Interceptor {


	public void intercept(Invocation ai) {

		Controller controller = ai.getController();
		
		// 如果没有，就设置一个
		Object key = controller.getSessionAttr(JFlyFoxUtils.USER_KEY);
		if (key==null) {
			controller.setSessionAttr(JFlyFoxUtils.USER_KEY, UUID.randomUUID().toString());
		}

		ai.invoke();
	}
}
