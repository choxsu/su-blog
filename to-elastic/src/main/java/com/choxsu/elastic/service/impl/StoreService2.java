package com.choxsu.elastic.service.impl;

import com.choxsu.elastic.service.IStoreService;
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
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 15:10
 */
@Slf4j
@Service("storeService2")
public class StoreService2 implements IStoreService {

    @Resource(name = "transportClient")
    private Client client;

    @Override
    public String save() {

        return "第二个实现";
    }

    @Override
    public Object find(String id) {
        return null;
    }

    @Override
    public Page<Map<String, Object>> queryStoreList(String keywords, Integer page, Integer size) {

        return PgBean.getPage(page, size);

    }
}
