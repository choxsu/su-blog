package com.choxsu.common.redis;

public interface RedisKey {

    /**
     * 首页显示数据前缀
     */
    String INDEX_KEY_PREFIX = "chosu:index:";

    /**
     * 是否是管理员数据前缀
     */
    String ACCOUNT_ROLE_KEY_PREFIX = "chosu:ar:";
}
