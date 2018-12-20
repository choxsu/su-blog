package com.choxsu.common.base;

import com.choxsu.common.entity.Account;
import com.choxsu._admin.login.AdminLoginService;
import com.choxsu.common.result.ResultModel;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;

/**
 * @author choxsu
 */
public class BaseController extends Controller {

    private Account loginAccount = null;

    @NotAction
    public Account getLoginAccount() {
        if (loginAccount == null) {
            loginAccount = getAttr(AdminLoginService.loginAccountCacheName);
            if (loginAccount != null && !loginAccount.isStatusOk()) {
                throw new IllegalStateException("当前用户状态不允许登录，status = " + loginAccount.getStatus());
            }
        }
        return loginAccount;
    }

    @NotAction
    public boolean isLogin() {
        return getLoginAccount() != null;
    }

    @NotAction
    public boolean notLogin() {
        return !isLogin();
    }

    /**
     * 获取登录账户id
     * 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
     * 也即确定已经是在登录后才可调用
     */
    @NotAction
    public int getLoginAccountId() {
        return getLoginAccount().getId();
    }


    //成功返回
    @NotAction
    public ResultModel success() {
        return ResultModel.success("成功");
    }

    @NotAction
    public ResultModel success(String msg) {
        return ResultModel.success(msg);
    }

    @NotAction
    public ResultModel success(Object data) {
        return ResultModel.success(data);
    }

    @NotAction
    public ResultModel success(String msg, Object data) {
        return ResultModel.success(msg, data);
    }

    //失败返回

    @NotAction
    public ResultModel fail() {
        return ResultModel.fail("失败");
    }

    @NotAction
    public ResultModel fail(String msg) {
        return ResultModel.fail(msg);
    }

    @NotAction
    public ResultModel fail(Object data) {
        return ResultModel.fail(data);
    }

    @NotAction
    public ResultModel fail(String msg, Object data) {
        return ResultModel.fail(msg, data);
    }

}
