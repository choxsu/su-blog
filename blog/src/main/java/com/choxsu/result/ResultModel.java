package com.choxsu.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * created by choxsu on 2018/5/10
 */
@Getter
@Setter
@NoArgsConstructor
public final class ResultModel extends HashMap {

    private static final String MSG_SUCCESS_CONSTANT = "成功";
    private static final String MSG_FAILED_CONSTANT = "失败";

    private static final String ERROR_CODE = "errorCode";
    private static final String DATA = "data";
    private static final String MSG = "msg";

    private static final int SUCCESS_STATUS = 1;
    private static final int FAILED_STATUS = 0;


    public static ResultModel success(String msg) {
        return new ResultModel(msg, SUCCESS_STATUS, null);
    }


    public static ResultModel success(Object data) {
        return new ResultModel(MSG_SUCCESS_CONSTANT, SUCCESS_STATUS, data);
    }

    public static ResultModel success(String msg, Object data) {
        return new ResultModel(msg, SUCCESS_STATUS, data);
    }

    public static ResultModel fail(String msg) {
        return new ResultModel(msg, FAILED_STATUS, null);
    }


    public static ResultModel fail(Object data) {
        return new ResultModel(MSG_FAILED_CONSTANT, FAILED_STATUS, data);
    }

    public static ResultModel fail(String msg, Object data) {
        return new ResultModel(msg, FAILED_STATUS, data);
    }


    public ResultModel(String msg, Integer errorCode, Object data) {
        put(MSG, msg);
        put(ERROR_CODE, errorCode);
        put(DATA, data);
    }

    public int getErrorCode() {
        return Integer.parseInt(get(ERROR_CODE).toString());
    }

    public String getMsg() {
        return get(MSG).toString();
    }

    public Object getData() {
        return get(DATA);
    }

    public boolean success() {
        return getErrorCode() == SUCCESS_STATUS;
    }

}
