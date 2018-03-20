package com.choxsu.web.admin.user;

import com.choxsu.util.StrUtils;
import com.choxsu.web.admin.annotation.ModelBind;
import com.choxsu.web.admin.common.base.SessionUser;

@ModelBind(table = "sys_user", key = "userid")
public class SysUser extends SessionUser<SysUser> {

	private static final long serialVersionUID = 1L;
	public static final SysUser dao = new SysUser();
	
	public Integer getUserid() {
		return getInt("userid") == null ? -1 : getInt("userid");
	}
	
	public String getUserName() {
		if (StrUtils.isNotEmpty(getStr("realname"))) {
			return getStr("realname");	
		}
		return getStr("username");
	}
}
