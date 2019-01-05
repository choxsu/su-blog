

package com.choxsu._admin.index;

import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.plugin.cron4j.Cron4jPlugin;

/**
 * 后台管理首页
 */
public class IndexAdminController extends BaseController {

	@Inject
	IndexAdminService srv;

	public void index() {
		setAttr("accountProfile", srv.getAccountProfile());
		setAttr("permissionProfile", srv.getPermissionProfile());
		setAttr("blogProfile", srv.getBlogProfile());
		setAttr("visitorProfile", srv.getVisitorProfile());
		render("index.html");
	}
}
