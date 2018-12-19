package com.choxsu.common.kit;


import java.util.HashMap;

/**
 * @author choxsu
 */
public class ResultKit extends HashMap {

    private static final String MSG = "msg";

    private static final String ERROR_CODE = "error_code";

    private static final String DATA = "data";

    private static final int SUCCESS_STATUS = 1;

    private static final int FAILED_STATUS = 0;

    private static final String SUCCESS_MSG = "成功";

    private static final String FAILED_MSG = "失败";

    public static ResultKit success(String msg) {
        return new ResultKit(msg, SUCCESS_STATUS, null);
    }

    public static ResultKit success(Object data) {
        return new ResultKit(SUCCESS_MSG, SUCCESS_STATUS, data);
    }

    public static ResultKit success(String msg, Object data) {
        return new ResultKit(msg, SUCCESS_STATUS, data);
    }

    public static ResultKit fail(String msg) {
        return new ResultKit(msg, FAILED_STATUS, null);
    }

    public static ResultKit fail(Object data) {
        return new ResultKit(FAILED_MSG, FAILED_STATUS, data);
    }

    public static ResultKit fail(String msg, Object data) {
        return new ResultKit(msg, FAILED_STATUS, data);
    }

    public boolean isSuccess() {
        Object o = get(ERROR_CODE);
        if (o == null)
            return false;

        return Integer.parseInt(o.toString()) == SUCCESS_STATUS;
    }


    public ResultKit(String msg, int errorCode, Object data) {

        super.put(MSG, msg);
        super.put(ERROR_CODE, errorCode);
        super.put(DATA, data);
    }


}
