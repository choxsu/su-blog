package com.choxsu._admin.role;

import com.choxsu._admin.auth.AdminAuthInterceptor;
import com.choxsu._admin.auth.AdminAuthService;
import com.choxsu.common.entity.Account;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 界面上的权限控制功能
 * 用来控制界面上的菜单、按钮等等元素的显示
 * <p>
 * 使用示例见模板文件： /_view/_admin/common/_menu.html 或者 /_view/_admin/permission/index.html
 * #role("权限管理员", "CEO", "CTO")
 * ...
 * #end
 */
public class RoleDirective extends Directive {

    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        Account account = AdminAuthInterceptor.getThreadLocalAccount();
        if (account != null && account.isStatusOk()) {
            // 如果是超级管理员，或者拥有指定的角色则放行
            if (AdminAuthService.me.isSuperAdmin(account.getId())) {
                stat.exec(env, scope, writer);
            } else {
                String[] roleNameArray = getRoleNameArray(exprList.evalExprList(scope));
                if (hasRole(account.getId(), roleNameArray)) {
                    stat.exec(env, scope, writer);
                }
            }
        }
    }

    /**
     * 当前账号是否拥有某些角色
     */
    public static boolean hasRole(int accountId, String[] roleNameArray) {
        if (roleNameArray == null || roleNameArray.length == 0) {
            return false;
        }
        //准备将角色优化到缓存里，降低数据库压力 TODO
        Kv data = Kv.by("accountId", accountId).set("roleNameArray", roleNameArray);
        SqlPara sp = Db.getSqlPara("admin.role.hasRole", data);
        Integer ret = Db.queryInt(sp.getSql(), sp.getPara());
        return ret != null;
    }

    /**
     * 将表达式求值后的数组转化成 roleName 数组
     */
    private String[] getRoleNameArray(Object[] values) {
        String[] roleNameArray = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof String) {
                roleNameArray[i] = ((String) values[i]).trim();
            } else {
                throw new IllegalArgumentException("角色名只能为 String 类型");
            }
        }
        return roleNameArray;
    }

    @Override
    public boolean hasEnd() {
        return true;
    }
}