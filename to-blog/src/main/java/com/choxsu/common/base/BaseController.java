package com.choxsu.common.base;

import com.choxsu.common.entity.Account;
import com.choxsu.login.LoginService;
import com.choxsu.result.ResultModel;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author choxsu
 */
public class BaseController extends Controller {

    private Account loginAccount = null;

    @NotAction
    public Account getLoginAccount() {
        if (loginAccount == null) {
            loginAccount = getAttr(LoginService.loginAccountCacheName);
            if (loginAccount != null && ! loginAccount.isStatusOk()) {
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


    private static final int SUCCESS = 1;
    private static final String SUCCESS_MSG = "OK";

    private static final int FAILED = 0;
    private static final String FAILED_MSG = "NO";

    /**
     * 返回成功 无参数
     *
     * @return Map
     */
    @NotAction
    public Map<String, Object> respSuccess() {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", SUCCESS_MSG);
        result.put("data", "");
        return result;
    }

    @NotAction
    public Map<String, Object> respSuccess(String msg) {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", msg);
        result.put("data", "");
        return result;
    }

    /**
     * 返回成功
     *
     * @param object 返回的结果对象
     * @return Map
     */
    @NotAction
    public Map<String, Object> respSuccess(Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", SUCCESS_MSG);
        result.put("data", object);
        return result;
    }

    /**
     * 返回成功
     *
     * @param object 返回的结果对象
     * @return Map
     */
    @NotAction
    public Map<String, Object> respSuccess(String msg, Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }


    @NotAction
    public Map<String, Object> respFail() {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", FAILED_MSG);
        result.put("data", "");
        return result;
    }

    @NotAction
    public Map<String, Object> respFail(String msg) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", msg);
        result.put("data", "");
        return result;
    }

    @NotAction
    public Map<String, Object> respFail(Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", FAILED_MSG);
        result.put("data", object);
        return result;
    }

    @NotAction
    public Map<String, Object> respFail(String msg, Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    @NotAction
    private Map<String, Object> getHashMap() {
        return new HashMap<String, Object>();
    }


    @NotAction
    public Map<String, Object> getSuccessApiResult(String msg, Object object){
        Map<String, Object> result = getHashMap();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    @NotAction
    public Map<String, Object> getFailApiResult(String msg, Object object){
        Map<String, Object> result = getHashMap();
        result.put("success", false);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }


}
