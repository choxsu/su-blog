

package com.choxsu._admin.index;


import com.choxsu.common.auto.Inject;
import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Clear;

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
		setAttr("blogCategoryProfile", srv.getBlogCategoryProfile());
		setAttr("blogTagProfile", srv.getBlogTagProfile());
		setAttr("visitorProfile", srv.getVisitorProfile());

		render("index.html");
	}
}
