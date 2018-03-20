package com.choxsu.web.admin.rolemenu;


import com.choxsu.component.base.BaseProjectModel;
import com.choxsu.web.admin.annotation.ModelBind;

@ModelBind(table = "sys_role_menu")
public class SysRoleMenu extends BaseProjectModel<SysRoleMenu> {

	private static final long serialVersionUID = 1L;
	public static final SysRoleMenu dao = new SysRoleMenu();

}
