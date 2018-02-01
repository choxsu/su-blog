package com.choxsu.common.entity;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * @author choxsu , do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("blog", "id", Blog.class);
		arp.addMapping("blog_account", "id", BlogAccount.class);
		arp.addMapping("blog_category", "id", BlogCategory.class);
		arp.addMapping("blog_tag", "id", BlogTag.class);
		arp.addMapping("sensitive_words", "id", SensitiveWords.class);
		arp.addMapping("visitor", "id", Visitor.class);
	}
}

