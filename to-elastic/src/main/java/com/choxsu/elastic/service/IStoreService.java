package com.choxsu.elastic.service;

import com.choxsu.elastic.util.PgBean;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 15:10
 */
public interface IStoreService {

    /**
     * save
     *
     * @return str id
     */
    String save();

    /**
     * find by id
     *
     * @param id id params
     * @return find object
     */
    Object find(String id);

    /**
     * list
     *
     * @param keywords 检索关键字
     * @param page     当前页
     * @param size     每页条数
     * @return 分页对象
     */
    Page<Map<String, Object>> queryStoreList(String keywords, Integer page, Integer size);
}
