package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
/**
 * @author choxsu, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBlogAccount<M extends BaseBlogAccount<M>> extends Model<M> implements IBean {

    /**
     * 主键id
     */
    private java.lang.String id = "id";
    /**
     * 昵称
     */
    private java.lang.String nickName = "nickName";
    /**
     * 用户名
     */
    private java.lang.String userName = "userName";
    /**
     * 密码 md5加密后
     */
    private java.lang.String password = "password";
    /**
     * 盐
     */
    private java.lang.String salt = "salt";
    /**
     * 状态 0-待激活 1-正常 2-禁用
     */
    private java.lang.String status = "status";
    /**
     * 创建时间
     */
    private java.lang.String createAt = "createAt";
    /**
     * ip地址
     */
    private java.lang.String ip = "ip";
    /**
     * 头像
     */
    private java.lang.String avatar = "avatar";
    /**
     * 被赞次数
     */
    private java.lang.String likeCount = "likeCount";


	public void setId(java.lang.Integer id) {
		set(this.id, id);
	}
	

	public java.lang.Integer getId() {
		return getInt(id);
	}


	public void setNickName(java.lang.String nickName) {
		set(this.nickName, nickName);
	}
	

	public java.lang.String getNickName() {
		return getStr(nickName);
	}


	public void setUserName(java.lang.String userName) {
		set(this.userName, userName);
	}
	

	public java.lang.String getUserName() {
		return getStr(userName);
	}


	public void setPassword(java.lang.String password) {
		set(this.password, password);
	}
	

	public java.lang.String getPassword() {
		return getStr(password);
	}


	public void setSalt(java.lang.String salt) {
		set(this.salt, salt);
	}
	

	public java.lang.String getSalt() {
		return getStr(salt);
	}


	public void setStatus(java.lang.Integer status) {
		set(this.status, status);
	}
	

	public java.lang.Integer getStatus() {
		return getInt(status);
	}


	public void setCreateAt(java.util.Date createAt) {
		set(this.createAt, createAt);
	}
	

	public java.util.Date getCreateAt() {
		return get(createAt);
	}


	public void setIp(java.lang.String ip) {
		set(this.ip, ip);
	}
	

	public java.lang.String getIp() {
		return getStr(ip);
	}


	public void setAvatar(java.lang.String avatar) {
		set(this.avatar, avatar);
	}
	

	public java.lang.String getAvatar() {
		return getStr(avatar);
	}


	public void setLikeCount(java.lang.Integer likeCount) {
		set(this.likeCount, likeCount);
	}
	

	public java.lang.Integer getLikeCount() {
		return getInt(likeCount);
	}

}
