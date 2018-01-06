package com.choxsu.common.constant;

/**
 * @author chox su
 * @date 2018/01/06 14:30
 */
public enum EnCacheEnum {

    TAGS("tagsName","tagsKey"),
    BLOG("blogName","blogKey");

    private String name;
    private String key;

    EnCacheEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
