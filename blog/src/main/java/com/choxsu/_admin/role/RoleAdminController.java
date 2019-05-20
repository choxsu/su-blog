

package com.choxsu._admin.role;

import com.choxsu._admin.permission.PermissionAdminService;
import com.choxsu._admin.permission.Remark;
import com.jfinal.aop.Inject;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Permission;
import com.choxsu.common.entity.Role;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 角色管理控制器
 */
public class RoleAdminController extends BaseController {

	@Inject
	RoleAdminService srv;
	@Inject
	PermissionAdminService permissionAdminService;


	@Remark("角色管理首页")
	public void index() {
		Page<Role> rolePage = srv.paginate(getParaToInt("p", 1));
		setAttr("rolePage", rolePage);
		render("index.html");
	}

	@Remark("角色添加页面")
	public void add() {
		render("add.html");
	}

	@Remark("角色保存")
	@Before(RoleAdminValidator.class)
	public void save() {
		Role role = getBean(Role.class);
		Ret ret = srv.save(role);
		renderJson(ret);
	}

	@Remark("角色编辑页面")
	public void edit() {
		keepPara("p");
		Role role = srv.findById(getParaToInt("id"));
		setAttr("role", role);
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Remark("角色更新")
	@Before(RoleAdminValidator.class)
	public void update() {
		Role role = getBean(Role.class);
		Ret ret = srv.update(role);
		renderJson(ret);
	}

	@Remark("角色删除")
	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}


	/**
	 * 分配权限
	 */
	@Remark("角色分配权限页面")
	public void assignPermissions() {
		Role role = srv.findById(getParaToInt("id"));
		List<Permission> permissionList = permissionAdminService.getAllPermissions();
		srv.markAssignedPermissions(role, permissionList);
		LinkedHashMap<String, List<Permission>> permissionMap = srv.groupByController(permissionList);

		setAttr("role", role);
		setAttr("permissionMap", permissionMap);
		render("assign_permissions.html");
	}

	/**
	 * 添加权限
	 */
	@Remark("角色权限增加")
	public void addPermission() {
		Ret ret = srv.addPermission(getParaToInt("roleId"), getParaToInt("permissionId"));
		renderJson(ret);
	}

	/**
	 * 删除权限
	 */
	@Remark("角色权限删除")
	public void deletePermission() {
		Ret ret = srv.deletePermission(getParaToInt("roleId"), getParaToInt("permissionId"));
		renderJson(ret);
	}
}
