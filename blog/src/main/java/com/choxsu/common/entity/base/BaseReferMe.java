package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseReferMe<M extends BaseReferMe<M>> extends Model<M> implements IBean {

	/**
	 * ID
	 */
	public void setId(Integer id) {
		set("id", id);
	}

	/**
	 * ID
	 */
	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 接收者账号id
	 */
	public void setReferAccountId(Integer referAccountId) {
		set("referAccountId", referAccountId);
	}

	/**
	 * 接收者账号id
	 */
	public Integer getReferAccountId() {
		return getInt("referAccountId");
	}

	/**
	 * newsFeedId
	 */
	public void setNewsFeedId(Integer newsFeedId) {
		set("newsFeedId", newsFeedId);
	}

	/**
	 * newsFeedId
	 */
	public Integer getNewsFeedId() {
		return getInt("newsFeedId");
	}

	/**
	 * @我、评论我等等的refer类型
	 */
	public void setType(Integer type) {
		set("type", type);
	}

	/**
	 * @我、评论我等等的refer类型
	 */
	public Integer getType() {
		return getInt("type");
	}

	/**
	 * 创建时间
	 */
	public void setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateAt() {
		return get("createAt");
	}

}