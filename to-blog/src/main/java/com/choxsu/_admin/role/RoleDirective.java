package com.choxsu._admin.role;

import com.choxsu._admin.auth.AdminAuthService;
import com.choxsu.common.entity.Account;
import com.choxsu.login.LoginService;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 界面上的权限控制功能
 * 用来控制界面上的菜单、按钮等等元素的显示
 * <p>
 * 使用示例见模板文件： /view/_admin/common/_menu.html 或者 /view/_admin/permission/index.html
 * #role("权限管理员", "CEO", "CTO")
 * ...
 * #end
 */
public class RoleDirective extends Directive {

    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        Account account = (Account)scope.getRootData().get(LoginService.loginAccountCacheName);
        if (account != null && account.isStatusOk()) {
            // 如果是超级管理员，或者拥有指定的角色则放行
            if (	AdminAuthService.me.isSuperAdmin(account.getId()) ||
                    AdminAuthService.me.hasRole(account.getId(), getRoleNameArray(scope))) {
                stat.exec(env, scope, writer);
            }
        }
    }

    /**
     * 从 #role 指令参数中获取角色名称数组
     */
    private String[] getRoleNameArray(Scope scope) {
        Object[] values = exprList.evalExprList(scope);
        String[] ret = new String[values.length];
        for (int i=0; i<values.length; i++) {
            if (values[i] instanceof String) {
                ret[i] = (String)values[i];
            } else {
                throw new IllegalArgumentException("角色名只能为 String 类型");
            }
        }
        return ret;
    }


    @Override
    public boolean hasEnd() {
        return true;
    }
}