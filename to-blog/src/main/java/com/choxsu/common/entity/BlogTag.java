package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseBlogTag;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class BlogTag extends BaseBlogTag<BlogTag> {
	public static final BlogTag dao = new BlogTag().dao();
}
