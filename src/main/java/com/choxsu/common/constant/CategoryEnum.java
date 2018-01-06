package com.choxsu.common.constant;

/**
 * @author chox su
 * @date 2018/01/06 10:48
 */
public enum CategoryEnum {
    /**
     * 类型 note（笔记）favorite(收藏）code(代码）about(关于）
     */
    BLOG("blog"),
    FAVORITE("favorite"),
    CODE("code"),
    ABOUT("about");

    private String name;

    CategoryEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
