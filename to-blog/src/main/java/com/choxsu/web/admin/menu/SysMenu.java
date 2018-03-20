package com.choxsu.web.admin.menu;

import com.choxsu.component.base.BaseProjectModel;
import com.choxsu.web.admin.annotation.ModelBind;

@ModelBind(table = "sys_menu")
public class SysMenu extends BaseProjectModel<SysMenu> {

	private static final long serialVersionUID = 1L;

	public static final SysMenu dao = new SysMenu();

}
