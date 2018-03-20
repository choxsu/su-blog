package com.choxsu.web.admin.role;


import com.choxsu.component.base.BaseProjectModel;
import com.choxsu.web.admin.annotation.ModelBind;

@ModelBind(table = "sys_role")
public class SysRole extends BaseProjectModel<SysRole> {

	private static final long serialVersionUID = 1L;
	public static final SysRole dao = new SysRole();

}
