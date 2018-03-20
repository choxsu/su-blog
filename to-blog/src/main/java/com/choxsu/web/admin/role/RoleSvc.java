package com.choxsu.web.admin.role;

import com.choxsu.util.DateUtils;
import com.choxsu.util.NumberUtils;
import com.choxsu.util.StrUtils;
import com.choxsu.web.admin.common.base.BaseService;
import com.choxsu.web.admin.rolemenu.SysRoleMenu;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

public class RoleSvc extends BaseService {

	/**
	 * 获取角色授权的菜单
	 * 
	 * 2015年4月28日 下午5:01:54 flyfox 369191470@qq.com
	 * 
	 * @param roleid
	 * @return
	 */
	public String getMemus(int roleid) {
		String menus = "";
		List<SysRoleMenu> roleMenuList = new SysRoleMenu().findByWhere("where roleid = ?", roleid);
		for (SysRoleMenu role : roleMenuList) {
			menus += role.get("menuid")+",";
		}
		if (menus != null) {
			menus = menus.substring(0,menus.length()-1);
		}
		return menus;
	}

	/**
	 * 保存授权信息
	 * 
	 * 2015年4月28日 下午5:00:30 flyfox 369191470@qq.com
	 * 
	 * @param roleid
	 * @param menus
	 */
	public void saveAuth(int roleid, String menus, int update_id) {
		// 删除原有数据库
		Db.update("delete from sys_role_menu where roleid = ? ", roleid);

		if (StrUtils.isNotEmpty(menus)) {
			String[] arr = menus.split(",");
			for (String menuid : arr) {
				SysRoleMenu roleMenu = new SysRoleMenu();
				roleMenu.set("roleid", roleid);
				roleMenu.set("menuid", NumberUtils.parseInt(menuid));

				// 日志添加
				roleMenu.put("update_id", update_id);
				roleMenu.put("update_time", DateUtils.getNow(DateUtils.DEFAULT_REGEX_YYYY_MM_DD_HH_MIN_SS));
				roleMenu.save();
			}
		}
	}

}
