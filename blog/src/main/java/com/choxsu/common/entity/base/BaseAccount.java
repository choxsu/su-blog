package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author choxsu, do not modify this file.
 * table: account
 * remarks: 
 */
@SuppressWarnings("serial")
public abstract class BaseAccount<M extends BaseAccount<M>> extends Model<M> {

    public static final String tableName = "account";

	/**
	 * ID
	 */
	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	/**
	 * ID
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 昵称
	 */
	public void setNickName(java.lang.String nickName) {
		set("nickName", nickName);
	}

	/**
	 * 昵称
	 */
	public java.lang.String getNickName() {
		return getStr("nickName");
	}

	/**
	 * 用户名
	 */
	public void setUserName(java.lang.String userName) {
		set("userName", userName);
	}

	/**
	 * 用户名
	 */
	public java.lang.String getUserName() {
		return getStr("userName");
	}

	/**
	 * 密码
	 */
	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	/**
	 * 密码
	 */
	public java.lang.String getPassword() {
		return getStr("password");
	}

	/**
	 * 盐
	 */
	public void setSalt(java.lang.String salt) {
		set("salt", salt);
	}

	/**
	 * 盐
	 */
	public java.lang.String getSalt() {
		return getStr("salt");
	}

	/**
	 * 状态 -1 锁定账号，无法做任何事情 0-注册、未激活 1-正常、已激活
	 */
	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	/**
	 * 状态 -1 锁定账号，无法做任何事情 0-注册、未激活 1-正常、已激活
	 */
	public java.lang.Integer getStatus() {
		return getInt("status");
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

	/**
	 * 注册IP
	 */
	public void setIp(java.lang.String ip) {
		set("ip", ip);
	}

	/**
	 * 注册IP
	 */
	public java.lang.String getIp() {
		return getStr("ip");
	}

	/**
	 * 头像
	 */
	public void setAvatar(java.lang.String avatar) {
		set("avatar", avatar);
	}

	/**
	 * 头像
	 */
	public java.lang.String getAvatar() {
		return getStr("avatar");
	}

	/**
	 * 被赞次数
	 */
	public void setLikeCount(java.lang.Integer likeCount) {
		set("likeCount", likeCount);
	}

	/**
	 * 被赞次数
	 */
	public java.lang.Integer getLikeCount() {
		return getInt("likeCount");
	}

	/**
	 * 是否第三方登陆 0-否 1-是
	 */
	public void setIsThird(java.lang.Integer isThird) {
		set("isThird", isThird);
	}

	/**
	 * 是否第三方登陆 0-否 1-是
	 */
	public java.lang.Integer getIsThird() {
		return getInt("isThird");
	}

}
