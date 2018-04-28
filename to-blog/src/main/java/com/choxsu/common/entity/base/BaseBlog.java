package com.choxsu.common.entity.base;

import com.choxsu.common.base.BaseModel;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
/**
 * @author choxsu, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBlog<M extends BaseBlog<M>> extends BaseModel<M> implements IBean {

    /**
     * 主键id
     */
    private java.lang.String id = "id";
    /**
     * 博客主id
     */
    private java.lang.String accountId = "accountId";
    /**
     * 标题
     */
    private java.lang.String title = "title";
    /**
     * 内容
     */
    private java.lang.String content = "content";
    /**
     * 创建时间
     */
    private java.lang.String createAt = "createAt";
    /**
     * 修改时间
     */
    private java.lang.String updateAt = "updateAt";
    /**
     * 点击次数
     */
    private java.lang.String clickCount = "clickCount";
    /**
     * 喜欢次数
     */
    private java.lang.String likeCount = "likeCount";
    /**
     * 收藏次数
     */
    private java.lang.String favoriteCount = "favoriteCount";
    /**
     * 类型 note（笔记）favorite(收藏）code(代码）about(关于）
     */
    private java.lang.String category = "category";
    /**
     * 是否删除 0否1是
     */
    private java.lang.String isDelete = "isDelete";
    /**
     * tag_id
     */
    private java.lang.String tagId = "tag_id";
    /**
     * 代码分类id，如果category为code时候，这个值才会生效
     */
    private java.lang.String categoryId = "category_id";


	public void setId(java.lang.Integer id) {
		set(this.id, id);
	}
	

	public java.lang.Integer getId() {
		return getInt(id);
	}


	public void setAccountId(java.lang.Integer accountId) {
		set(this.accountId, accountId);
	}
	

	public java.lang.Integer getAccountId() {
		return getInt(accountId);
	}


	public void setTitle(java.lang.String title) {
		set(this.title, title);
	}
	

	public java.lang.String getTitle() {
		return getStr(title);
	}


	public void setContent(java.lang.String content) {
		set(this.content, content);
	}
	

	public java.lang.String getContent() {
		return getStr(content);
	}


	public void setCreateAt(java.util.Date createAt) {
		set(this.createAt, createAt);
	}
	

	public java.util.Date getCreateAt() {
		return get(createAt);
	}


	public void setUpdateAt(java.util.Date updateAt) {
		set(this.updateAt, updateAt);
	}
	

	public java.util.Date getUpdateAt() {
		return get(updateAt);
	}


	public void setClickCount(java.lang.Integer clickCount) {
		set(this.clickCount, clickCount);
	}
	

	public java.lang.Integer getClickCount() {
		return getInt(clickCount);
	}


	public void setLikeCount(java.lang.Integer likeCount) {
		set(this.likeCount, likeCount);
	}
	

	public java.lang.Integer getLikeCount() {
		return getInt(likeCount);
	}


	public void setFavoriteCount(java.lang.Integer favoriteCount) {
		set(this.favoriteCount, favoriteCount);
	}
	

	public java.lang.Integer getFavoriteCount() {
		return getInt(favoriteCount);
	}


	public void setCategory(java.lang.String category) {
		set(this.category, category);
	}
	

	public java.lang.String getCategory() {
		return getStr(category);
	}


	public void setIsDelete(java.lang.Integer isDelete) {
		set(this.isDelete, isDelete);
	}
	

	public java.lang.Integer getIsDelete() {
		return getInt(isDelete);
	}


	public void setTagId(java.lang.Integer tagId) {
		set(this.tagId, tagId);
	}
	

	public java.lang.Integer getTagId() {
		return getInt(tagId);
	}


	public void setCategoryId(java.lang.Integer categoryId) {
		set(this.categoryId, categoryId);
	}
	

	public java.lang.Integer getCategoryId() {
		return getInt(categoryId);
	}

}
