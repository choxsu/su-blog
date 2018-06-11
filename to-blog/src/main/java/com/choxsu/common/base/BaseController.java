package com.choxsu.common.base;

import com.choxsu.common.entity.Account;
import com.choxsu.login.LoginService;
import com.choxsu.result.ResultModel;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.NotAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author choxsu
 */
public class BaseController extends Controller {

    private Account loginAccount = null;

    @Before(NotAction.class)
    public Account getLoginAccount() {
        if (loginAccount == null) {
            loginAccount = getAttr(LoginService.loginAccountCacheName);
            if (loginAccount != null && ! loginAccount.isStatusOk()) {
                throw new IllegalStateException("当前用户状态不允许登录，status = " + loginAccount.getStatus());
            }
        }
        return loginAccount;
    }

    @Before(NotAction.class)
    public boolean isLogin() {
        return getLoginAccount() != null;
    }

    @Before(NotAction.class)
    public boolean notLogin() {
        return !isLogin();
    }

    /**
     * 获取登录账户id
     * 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
     * 也即确定已经是在登录后才可调用
     */
    @Before(NotAction.class)
    public int getLoginAccountId() {
        return getLoginAccount().getId();
    }


    //成功返回

    public ResultModel success() {
        return ResultModel.success("成功");
    }

    public ResultModel success(String msg) {
        return ResultModel.success(msg);
    }

    public ResultModel success(Object data) {
        return ResultModel.success(data);
    }

    public ResultModel success(String msg, Object data) {
        return ResultModel.success(msg, data);
    }

    //失败返回

    public ResultModel fail() {
        return ResultModel.fail("失败");
    }

    public ResultModel fail(String msg) {
        return ResultModel.fail(msg);
    }

    public ResultModel fail(Object data) {
        return ResultModel.fail(data);
    }

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
    public Map<String, Object> respSuccess() {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", SUCCESS_MSG);
        result.put("data", "");
        return result;
    }

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
    public Map<String, Object> respSuccess(String msg, Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", SUCCESS);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }


    public Map<String, Object> respFail() {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", FAILED_MSG);
        result.put("data", "");
        return result;
    }

    public Map<String, Object> respFail(String msg) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", msg);
        result.put("data", "");
        return result;
    }

    public Map<String, Object> respFail(Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", FAILED_MSG);
        result.put("data", object);
        return result;
    }

    public Map<String, Object> respFail(String msg, Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", FAILED);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    private Map<String, Object> getHashMap() {
        return new HashMap<String, Object>();
    }


    public Map<String, Object> getSuccessApiResult(String msg, Object object){
        Map<String, Object> result = getHashMap();
        result.put("success", true);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    public Map<String, Object> getFailApiResult(String msg, Object object){
        Map<String, Object> result = getHashMap();
        result.put("success", false);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }


}
