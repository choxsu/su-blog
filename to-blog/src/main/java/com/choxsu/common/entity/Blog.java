package com.choxsu.common.entity;


import com.choxsu.common.entity.base.BaseBlog;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class Blog extends BaseBlog<Blog> {
	public static final Blog dao = new Blog().dao();
}