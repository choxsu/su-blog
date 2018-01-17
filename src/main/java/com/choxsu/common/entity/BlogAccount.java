package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseBlogAccount;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class BlogAccount extends BaseBlogAccount<BlogAccount> {
	public static final BlogAccount dao = new BlogAccount().dao();
}
