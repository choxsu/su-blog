package com.choxsu._admin.permission;

import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Permission;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

/**
 * 权限管理
 */
public class PermissionAdminController extends BaseController {

	@Inject
	PermissionAdminService srv;

	public void index() {
		Page<Permission> permissionPage = srv.paginate(getParaToInt("p", 1));
		srv.replaceControllerPrefix(permissionPage, "com.choxsu._admin.", "...");
		boolean hasRemovedPermission = srv.markRemovedActionKey(permissionPage);
		setAttr("permissionPage", permissionPage);
		setAttr("hasRemovedPermission", hasRemovedPermission);
		render("index.html");
	}

	public void sync() {
		Ret ret = srv.sync();
		renderJson(ret);
	}

	public void edit() {
		keepPara("p");
		Permission permission = srv.findById(getParaToInt("id"));
		setAttr("permission", permission);
		render("edit.html");
	}

	public void update() {
		Ret ret = srv.update(getBean(Permission.class));
		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}
}