package com.choxsu.component.interceptor;

import com.choxsu.web.admin.common.base.BaseController;
import com.choxsu.web.admin.common.base.BaseForm;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 公共属性拦截器
 * 
 * @author flyfox 2014-2-11
 */
public class CommonInterceptor implements Interceptor {


	public void intercept(Invocation ai) {

			Controller controller = ai.getController();

			// 设置公共属性
			if (controller instanceof BaseController) {
				BaseForm form = ((BaseController) controller).getModelByForm(BaseForm.class);
				controller.setAttr("form", form);
			}

			ai.invoke();
		}
}
