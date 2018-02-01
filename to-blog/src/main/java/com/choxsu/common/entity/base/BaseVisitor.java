package com.choxsu.common.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;
/**
 * @author choxsu, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseVisitor<M extends BaseVisitor<M>> extends Model<M> implements IBean {

    /**
     * 主键id
     */
    private java.lang.String id = "id";
    /**
     * 请求的IP地址
     */
    private java.lang.String ip = "ip";
    /**
     * 请求的页面路径
     */
    private java.lang.String url = "url";
    /**
     * 请求方法
     */
    private java.lang.String method = "method";
    /**
     * 请求的客户端
     */
    private java.lang.String client = "client";
    /**
     * 请求时间
     */
    private java.lang.String requestTime = "requestTime";


	public void setId(java.lang.Integer id) {
		set(this.id, id);
	}
	

	public java.lang.Integer getId() {
		return getInt(id);
	}


	public void setIp(java.lang.String ip) {
		set(this.ip, ip);
	}
	

	public java.lang.String getIp() {
		return getStr(ip);
	}


	public void setUrl(java.lang.String url) {
		set(this.url, url);
	}
	

	public java.lang.String getUrl() {
		return getStr(url);
	}


	public void setMethod(java.lang.String method) {
		set(this.method, method);
	}
	

	public java.lang.String getMethod() {
		return getStr(method);
	}


	public void setClient(java.lang.String client) {
		set(this.client, client);
	}
	

	public java.lang.String getClient() {
		return getStr(client);
	}


	public void setRequestTime(java.util.Date requestTime) {
		set(this.requestTime, requestTime);
	}
	

	public java.util.Date getRequestTime() {
		return get(requestTime);
	}

}
