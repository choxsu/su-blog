package com.choxsu.admin.permission;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Permission;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 权限管理
 */
public class PermissionAdminController extends BaseController {

	@Inject
    PermissionAdminService srv;

	@Remark("权限首页")
	public void index() {
		Page<Permission> permissionPage = srv.paginate(getParaToInt("p", 1));
		srv.replaceControllerPrefix(permissionPage, "com.choxsu.admin.", "...");
		boolean hasRemovedPermission = srv.markRemovedActionKey(permissionPage);
		setAttr("permissionPage", permissionPage);
		setAttr("hasRemovedPermission", hasRemovedPermission);
		render("index.html");
	}

	@Remark("权限同步")
	public void sync() {
		Ret ret = srv.sync();
		renderJson(ret);
	}

	@Remark("权限编辑页面")
	public void edit() {
		keepPara("p");
		Permission permission = srv.findById(getParaToInt("id"));
		setAttr("permission", permission);
		render("edit.html");
	}
	@Remark("权限更新")
	public void update() {
		Ret ret = srv.update(getBean(Permission.class));
		renderJson(ret);
	}
	@Remark("权限删除")
	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}
}