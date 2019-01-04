/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 *
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 *
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 *
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */
package com.choxsu._admin.index;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;

/**
 * 首页业务
 */
public class IndexAdminService {


	public Ret getAccountProfile() {
		Ret ret = Ret.by("title", "账户总数");
		Integer total = Db.queryInt("select count(*) from account");
		ret.set("total", total);
		return ret;
	}

	public Ret getPermissionProfile() {
		Ret ret = Ret.by("title", "权限总数");
		Integer total = Db.queryInt("select count(*) from permission");
		ret.set("total", total);
		return ret;
	}

	public Ret getBlogProfile() {
		Ret ret = Ret.by("title", "博客总数");
		Integer total = Db.queryInt("select count(*) from blog where isDelete = 0 ");
		ret.set("total", total);
		return ret;
	}

	public Ret getBlogCategoryProfile() {
		Ret ret = Ret.by("title", "分类总数");
		Integer total = Db.queryInt("select count(*) from blog_category where status = 0 ");
		ret.set("total", total);
		return ret;
	}

	public Ret getBlogTagProfile() {
		Ret ret = Ret.by("title", "标签总数");
		Integer total = Db.queryInt("select count(*) from blog_tag where status = 0 ");
		ret.set("total", total);
		return ret;
	}


	public Ret getVisitorProfile() {
		Ret ret = Ret.by("title", "访问总数");
		Integer total = Db.queryInt("select count(*) from visitor");
		ret.set("total", total);
		return ret;
	}


}