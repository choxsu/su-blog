package com.choxsu.common.entity;

import com.choxsu.common.entity.base.BaseSensitiveWords;

/**
 * @author choxsu
 */
@SuppressWarnings("serial")
public class SensitiveWords extends BaseSensitiveWords<SensitiveWords> {
	public static final SensitiveWords dao = new SensitiveWords().dao();
}
