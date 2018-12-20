package com.choxsu._admin.auth;

import com.choxsu.common.entity.Account;
import com.choxsu._admin.login.AdminLoginService;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Ret;

/**
 * 后台权限管理拦截器
 *
 * @author choxsu
 */
public class AdminAuthInterceptor implements Interceptor {

    @Inject
    AdminAuthService srv;

    /**
     * 用于 sharedObject、sharedMethod 扩展中使用
     */
    private static final ThreadLocal<Account> threadLocal = new ThreadLocal<>();

    private static final String loginActionKey = "/admin/login";

    public static Account getThreadLocalAccount() {
        return threadLocal.get();
    }

    @Override
    public void intercept(Invocation inv) {
        Account loginAccount = inv.getController().getAttr(AdminLoginService.loginAccountCacheName);
        if (loginAccount != null && loginAccount.isStatusOk()) {
            // 传递给 sharedObject、sharedMethod 扩展使用
            threadLocal.set(loginAccount);

            // 如果是超级管理员或者拥有对当前 action 的访问权限则放行
            if (srv.isSuperAdmin(loginAccount.getId()) ||
                    srv.hasPermission(loginAccount.getId(), inv.getActionKey())) {
                inv.invoke();
                return;
            }
        }
        String actionKey = inv.getActionKey();

        if (loginAccount == null && !loginActionKey.equals(actionKey)) {
            inv.getController().redirect(loginActionKey);
        } else if (loginActionKey.equals(actionKey)) {
            inv.invoke();
        }
        // renderJson 提示没有操作权限，提升用户体验
        else {
            inv.getController().renderJson(Ret.fail("msg", "没有操作权限"));
        }
    }
}

