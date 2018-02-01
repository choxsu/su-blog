package com.choxsu.common;

import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author choxsu
 */
public class BaseController extends Controller {

    private static Map result = new HashMap(20);

    /**
     * 返回成功 无参数
     * @return Map
     */
    public Map respSuccess(){
        result.put("type", 1);
        result.put("msg", "OK");
        result.put("data", "");
        return result;
    }

    /**
     * 返回成功
     * @param object    返回的结果对象
     * @return Map
     */
    public Map respSuccess(Object object){
        result.put("type", 1);
        result.put("msg", "OK");
        result.put("data", object);
        return result;
    }

}
