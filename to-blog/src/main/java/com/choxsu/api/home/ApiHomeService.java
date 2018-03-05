package com.choxsu.api.home;

import com.choxsu.common.es.EsPlugin;
import com.choxsu.common.kit.PgBeanKit;
import com.jfinal.plugin.activerecord.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2018/03/02 11:33
 */
@Slf4j
public class ApiHomeService {

    private TransportClient client = EsPlugin.getClient();

    public Page<Map<String, Object>> getStorePage(String keywords, int page, int size) {
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
            int totalHits = ((int) hits.totalHits);
            return PgBeanKit.getPage(result, page, size, totalHits);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
