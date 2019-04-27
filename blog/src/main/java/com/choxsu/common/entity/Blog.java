package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseBlog;
import com.choxsu.common.safe.JsoupFilter;
import lombok.Data;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
@Data
public class Blog extends BaseBlog<Blog> {

    public static final String defaultTag = "Java";

    /**
     * 作者
     */
    private String author;
    /**
     * 标签名称
     */
    private String tagName;

    @Override
    protected void filter(int filterBy) {
        JsoupFilter.filterArticle(this);
    }
}
