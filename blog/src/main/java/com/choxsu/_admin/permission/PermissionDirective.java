package com.choxsu._admin.permission;

import com.choxsu._admin.auth.AdminAuthService;
import com.choxsu._front.login.LoginService;
import com.choxsu.common.entity.Account;
import com.jfinal.aop.Aop;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 界面上的权限控制功能
 * 用来控制界面上的菜单、按钮等等元素的显示
 * <p>
 * 使用示例见模板文件： /view/_admin/project/index.html 或者 /view/_admin/permission/index.html
 * #permission("/admin/project/edit")
 * <a href="/admin/project/edit?id=#(x.id)">
 * <i class="fa fa-pencil" title="修改"></i>
 * </a>
 * #end
 * <p>
 * 别名： #perm() #end
 */
public class PermissionDirective extends Directive {

    static AdminAuthService adminAuthService = Aop.get(AdminAuthService.class);

    public void exec(Env env, Scope scope, Writer writer) {
        Account account = (Account)scope.getRootData().get(LoginService.loginAccountCacheName);
        if (account != null && account.isStatusOk()) {
            // 如果是超级管理员，或者拥有指定的权限则放行
            if (adminAuthService.isSuperAdmin(account.getId()) ||
                    adminAuthService.hasPermission(account.getId(), getPermission(scope))) {
                stat.exec(env, scope, writer);
            }
        }
    }


    /**
     * 从 #permission 指令参数中获取 permission
     */
    private String getPermission(Scope scope) {
        Object value = exprList.eval(scope);
        if (value instanceof String) {
            return (String)value;
        } else {
            throw new IllegalArgumentException("权限参数只能为 String 类型");
        }
    }


    @Override
    public boolean hasEnd() {
        return true;
    }
}