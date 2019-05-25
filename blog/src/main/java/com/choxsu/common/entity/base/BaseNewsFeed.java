package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNewsFeed<M extends BaseNewsFeed<M>> extends Model<M> implements IBean {

	public void setId(Integer id) {
		set("id", id);
	}

	public Integer getId() {
		return getInt("id");
	}

	/**
	 * 动态创建者
	 */
	public void setAccountId(Integer accountId) {
		set("accountId", accountId);
	}

	/**
	 * 动态创建者
	 */
	public Integer getAccountId() {
		return getInt("accountId");
	}

	/**
	 * 动态引用类型
	 */
	public void setRefType(Integer refType) {
		set("refType", refType);
	}

	/**
	 * 动态引用类型
	 */
	public Integer getRefType() {
		return getInt("refType");
	}

	/**
	 * 动态引用所关联的 id
	 */
	public void setRefId(Integer refId) {
		set("refId", refId);
	}

	/**
	 * 动态引用所关联的 id
	 */
	public Integer getRefId() {
		return getInt("refId");
	}

	/**
	 * reply所属的贴子类型, 与type 字段填的值一样
	 */
	public void setRefParentType(Integer refParentType) {
		set("refParentType", refParentType);
	}

	/**
	 * reply所属的贴子类型, 与type 字段填的值一样
	 */
	public Integer getRefParentType() {
		return getInt("refParentType");
	}

	public void setRefParentId(Integer refParentId) {
		set("refParentId", refParentId);
	}

	public Integer getRefParentId() {
		return getInt("refParentId");
	}

	/**
	 * 动态创建时间
	 */
	public void setCreateAt(java.util.Date createAt) {
		set("createAt", createAt);
	}
	
	/**
	 * 动态创建时间
	 */
	public java.util.Date getCreateAt() {
		return get("createAt");
	}

}
