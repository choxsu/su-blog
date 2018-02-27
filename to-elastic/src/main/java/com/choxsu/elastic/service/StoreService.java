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
@Slf4j
@Service
public class StoreService {

    @Autowired
    private TransportClient client;

    @Before(Tx.class)
    public String save() {
        Record record = new Record();
        final String[] id = new String[1];
        Db.tx(() -> {
            record.set("address", "九龙坡区谢家湾正街55号华润中心万象城L5层(谢家湾轻轨站旁)");
            record.set("name", "大食代美食广场(万象城店)");
            record.set("telephone", "(023)68086707");
            Db.save("store", record);

            IndexResponse response = client.prepareIndex("choxsu", "store").setSource(record).get();
            id[0] = response.getId();
            return true;
        });

        return id[0];
    }

    public Object find(String id) {
        GetResponse response = client.prepareGet("choxsu", "store", id).get();
        Map<String, Object> source = response.getSource();
        if (source != null) {
            source.put("_id", response.getId());
        }
        return response;
    }

    public Page queryStoreList(String keywords, Integer page, Integer size) {

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            if (StringUtils.isNoneBlank(keywords)) {
                boolBuilder.should(QueryBuilders.matchQuery("store_name", keywords));
                boolBuilder.should(QueryBuilders.matchQuery("tag", keywords));
                boolBuilder.should(QueryBuilders.matchQuery("address", keywords));
            }
            SearchRequestBuilder builder = client.prepareSearch("store_list")
                    .setTypes("jdbc")
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setQuery(boolBuilder)
                    .setTimeout(TimeValue.timeValueMinutes(1))
                    .setFrom((page - 1) * size)
                    .setSize(size);
            log.info("builder info:{}", String.valueOf(builder));
            SearchResponse response = builder.get();
            SearchHits hits = response.getHits();

            hits.forEach((s) -> {
                Map<String, Object> sourceAsMap = s.getSourceAsMap();
                sourceAsMap.put("id", s.getId());
                result.add(sourceAsMap);
            });
            //List<T> list, int pageNumber, int pageSize, int totalPage, int totalRow
            int totalHits = ((int) hits.totalHits);
            return PgBean.getPage(result, page, size, totalHits);// new Page<Map<String, Object>>(result, page, size, (totalHits + size - 1) / size, totalHits);

        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
