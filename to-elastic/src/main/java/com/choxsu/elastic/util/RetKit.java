package com.choxsu.elastic.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/21 12:40
 */
public class RetKit extends HashMap {

    private static final String STATE = "state";
    private static final String STATE_OK = "ok";
    private static final String STATE_FAIL = "fail";

    // 以下为旧工作模式下的常量名
    private static final String OLD_STATE_OK = "isOk";
    private static final String OLD_STATE_FAIL = "isFail";

    // 默认为新工作模式
    private static boolean newWorkMode = true;

    /**
     * 设置为旧工作模式，为了兼容 jfinal 3.2 之前的版本
     */
    public static void setToOldWorkMode() {
        newWorkMode = false;
    }

    public RetKit() {
    }

    public static RetKit by(Object key, Object value) {
        return new RetKit().set(key, value);
    }

    public static RetKit create(Object key, Object value) {
        return new RetKit().set(key, value);
    }

    public static RetKit create() {
        return new RetKit();
    }

    public static RetKit ok() {
        return new RetKit().setOk();
    }

    public static RetKit ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static RetKit fail() {
        return new RetKit().setFail();
    }

    public static RetKit fail(Object key, Object value) {
        return fail().set(key, value);
    }

    public RetKit setOk() {
        if (newWorkMode) {
            super.put(STATE, STATE_OK);
        } else {
            super.put(OLD_STATE_OK, Boolean.TRUE);
            super.put(OLD_STATE_FAIL, Boolean.FALSE);
        }
        return this;
    }

    public RetKit setFail() {
        if (newWorkMode) {
            super.put(STATE, STATE_FAIL);
        } else {
            super.put(OLD_STATE_FAIL, Boolean.TRUE);
            super.put(OLD_STATE_OK, Boolean.FALSE);
        }
        return this;
    }

    public boolean isOk() {
        if (newWorkMode) {
            Object state = get(STATE);
            if (STATE_OK.equals(state)) {
                return true;
            }
            if (STATE_FAIL.equals(state)) {
                return false;
            }
        } else {
            Boolean isOk = (Boolean)get(OLD_STATE_OK);
            if (isOk != null) {
                return isOk;
            }
            Boolean isFail = (Boolean)get(OLD_STATE_FAIL);
            if (isFail != null) {
                return !isFail;
            }
        }

        throw new IllegalStateException("调用 isOk() 之前，必须先调用 ok()、fail() 或者 setOk()、setFail() 方法");
    }

    public boolean isFail() {
        if (newWorkMode) {
            Object state = get(STATE);
            if (STATE_FAIL.equals(state)) {
                return true;
            }
            if (STATE_OK.equals(state)) {
                return false;
            }
        } else {
            Boolean isFail = (Boolean)get(OLD_STATE_FAIL);
            if (isFail != null) {
                return isFail;
            }
            Boolean isOk = (Boolean)get(OLD_STATE_OK);
            if (isOk != null) {
                return !isOk;
            }
        }

        throw new IllegalStateException("调用 isFail() 之前，必须先调用 ok()、fail() 或者 setOk()、setFail() 方法");
    }

    public RetKit set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public RetKit set(Map map) {
        super.putAll(map);
        return this;
    }

    public RetKit set(RetKit RetKit) {
        super.putAll(RetKit);
        return this;
    }

    public RetKit delete(Object key) {
        super.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T)get(key);
    }

    public String getStr(Object key) {
        Object s = get(key);
        return s != null ? s.toString() : null;
    }

    public Integer getInt(Object key) {
        Number n = (Number)get(key);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(Object key) {
        Number n = (Number)get(key);
        return n != null ? n.longValue() : null;
    }

    public Number getNumber(Object key) {
        return (Number)get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean)get(key);
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean)value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean)value == false));
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public boolean equals(Object RetKit) {
        return RetKit instanceof RetKit && super.equals(RetKit);
    }
}
