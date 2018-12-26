package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseBlog;
import com.choxsu.common.safe.JsoupFilter;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {

    public static final String defaultTag = "Java";

    @Override
    protected void filter(int filterBy) {
        JsoupFilter.filterArticle(this);
    }
}
