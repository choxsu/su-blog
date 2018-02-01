package com.choxsu.common;

import com.jfinal.core.Controller;
import com.sun.scenario.effect.impl.prism.PrImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author choxsu
 */
public class BaseController extends Controller {

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


    public Map<String, Object> respFail(Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", 0);
        result.put("msg", FAILED_MSG);
        result.put("data", object);
        return result;
    }

    public Map<String, Object> respFail(String msg, Object object) {
        Map<String, Object> result = getHashMap();
        result.put("type", 0);
        result.put("msg", msg);
        result.put("data", object);
        return result;
    }

    private Map<String, Object> getHashMap() {
        return new HashMap<>();
    }
}
