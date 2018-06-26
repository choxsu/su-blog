package com.choxsu._admin.auth;


import com.choxsu.common.entity.Account;

/**
 * 权限管理的 shared method 扩展
 * <p>
 * 作为 #role、#permission 指令的补充，支持 #else 块
 * <p>
 * <p>
 * 使用示例：
 * #if (hasRole("管理员"))
 * ...
 * #else
 * ...
 * #end
 * <p>
 * #if (hasPermission("/admin/project/edit"))
 * ...
 * #else
 * ...
 * #end
 */
public class AdminAuthKit {

    /**
     * 当前账号是否拥有某些角色
     */
    public boolean hasRole(String... roleNameArray) {
        Account account = AdminAuthInterceptor.getThreadLocalAccount();
        if (account != null && account.isStatusOk()) {
            if (AdminAuthService.me.isSuperAdmin(account.getId()) ||
                    AdminAuthService.me.hasRole(account.getId(), roleNameArray)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否拥有具体某个权限
     */
    public boolean hasPermission(String actionKey) {
        Account account = AdminAuthInterceptor.getThreadLocalAccount();
        if (account != null && account.isStatusOk()) {
            if (AdminAuthService.me.isSuperAdmin(account.getId()) ||
                    AdminAuthService.me.hasPermission(account.getId(), actionKey)) {
                return true;
            }
        }

        return false;
    }
}


