package com.choxsu.elastic.dao;

import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2017/12/23 16:01
 */
public interface StoreDao {
    String save(Record record);

    String update(Record record);

    String deltele(String id);

    Object find(String id);

    Object query(Record record, int page, int size);
}
