package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseBlogCategory;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class BlogCategory extends BaseBlogCategory<BlogCategory> {
	public static final BlogCategory dao = new BlogCategory().dao();
}
