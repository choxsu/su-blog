package com.choxsu.web.admin.dict;

import com.choxsu.component.base.BaseProjectModel;
import com.choxsu.web.admin.annotation.ModelBind;

@ModelBind(table = "sys_dict", key = "dict_id")
public class SysDict extends BaseProjectModel<SysDict> {

	private static final long serialVersionUID = 1L;
	public static final SysDict dao = new SysDict();

}
