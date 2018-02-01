package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
/**
 * @author choxsu, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBlogCategory<M extends BaseBlogCategory<M>> extends Model<M> implements IBean {

    /**
     * 主键id
     */
    private java.lang.String id = "id";
    /**
     * 名称
     */
    private java.lang.String name = "name";
    /**
     * 是否有效；0是1否
     */
    private java.lang.String status = "status";


	public void setId(java.lang.Integer id) {
		set(this.id, id);
	}
	

	public java.lang.Integer getId() {
		return getInt(id);
	}


	public void setName(java.lang.String name) {
		set(this.name, name);
	}
	

	public java.lang.String getName() {
		return getStr(name);
	}


	public void setStatus(java.lang.Integer status) {
		set(this.status, status);
	}
	

	public java.lang.Integer getStatus() {
		return getInt(status);
	}

}
