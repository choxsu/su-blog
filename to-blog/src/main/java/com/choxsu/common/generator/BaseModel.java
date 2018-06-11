package com.choxsu.common.generator;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 封装 一些常用的方法
 *
 * @author choxsu
 */
public class BaseModel<M extends Model> extends Model<M> implements IBean {

    public String getTableName() {
        String name = _getTable().getName();
        return name;
    }

    /**
     * 查询所有
     * @return
     */
    public List<M> findAll() {
        String sql = "select * from " + getTableName();
        return find(sql);
    }


}
