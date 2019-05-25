package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRemind<M extends BaseRemind<M>> extends Model<M> implements IBean {

	/**
	 * 用户账号id，必须手动指定，不自增
	 */
	public void setAccountId(Integer accountId) {
		set("accountId", accountId);
	}

	/**
	 * 用户账号id，必须手动指定，不自增
	 */
	public Integer getAccountId() {
		return getInt("accountId");
	}

	/**
	 * 提到我的消息条数
	 */
	public void setReferMe(Integer referMe) {
		set("referMe", referMe);
	}

	/**
	 * 提到我的消息条数
	 */
	public Integer getReferMe() {
		return getInt("referMe");
	}

	/**
	 * 私信条数
	 */
	public void setMessage(Integer message) {
		set("message", message);
	}

	/**
	 * 私信条数
	 */
	public Integer getMessage() {
		return getInt("message");
	}

	/**
	 * 粉丝增加个数
	 */
	public void setFans(Integer fans) {
		set("fans", fans);
	}

	/**
	 * 粉丝增加个数
	 */
	public Integer getFans() {
		return getInt("fans");
	}

}
