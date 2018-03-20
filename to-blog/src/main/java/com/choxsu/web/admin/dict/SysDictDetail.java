package com.choxsu.web.admin.dict;

import com.choxsu.component.base.BaseProjectModel;
import com.choxsu.web.admin.annotation.ModelBind;

@ModelBind(table = "sys_dict_detail", key = "detail_id")
public class SysDictDetail extends BaseProjectModel<SysDictDetail> {

	private static final long serialVersionUID = 1L;
	public static final SysDictDetail dao = new SysDictDetail();

}
