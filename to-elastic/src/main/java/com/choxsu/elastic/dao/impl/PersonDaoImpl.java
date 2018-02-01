package com.choxsu.elastic.dao.impl;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.choxsu.elastic.dao.PersonDao;
import com.choxsu.elastic.entity.Person;

import javax.annotation.Resource;

/**
 * @author chox su
 * @date 2017/12/21 11:34
 */
@Component
public class PersonDaoImpl implements PersonDao {

    @Resource
    private TransportClient transportClient;

    private static final Logger log = Logger.getLogger(PersonDaoImpl.class);

    /**
     * 索引名称（数据库名）
     */
    private String index = "test";
    /**
     * 类型名称（表名）
     */
    private String type = "person";


    /**
     * 保存
     */
    @Override
    public String save(Person person) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("name", person.getName());
            builder.field("age", person.getAge());
            builder.field("sex", person.getSex());
            builder.field("birthday", person.getBirthday());
            builder.field("introduce", person.getIntroduce());
            builder.endObject();
            IndexResponse response = transportClient.prepareIndex(index, type).setSource(builder).get();
            return response.getId();
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Map butchSave(List list) {

        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();


        return null;
    }

    @Override
    public String update(Person person) {
        UpdateRequest request = new UpdateRequest(index, type, person.getId());
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (person.getName() != null) {
                builder.field("name", person.getName());
            }
            if (person.getSex() != null) {
                builder.field("sex", person.getSex());
            }
            if (person.getIntroduce() != null) {
                builder.field("introduce", person.getIntroduce());
            }
            if (person.getBirthday() != null) {
                builder.field("birthday", person.getBirthday());
            }
            if (person.getAge() > 0) {
                builder.field("age", person.getAge());
            }
            builder.endObject();
            request.doc(builder);
            UpdateResponse response = transportClient.update(request).get();
            return response.getId();
        } catch (IOException | InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String deltele(String id) {
        DeleteResponse response = transportClient.prepareDelete(index, type, id).get();
        return response.getId();
    }

    @Override
    public Object find(String id) {
        GetResponse response = transportClient.prepareGet(index, type, id).get();
        Map<String, Object> result = response.getSource();
        if (result != null) {
            result.put("_id", response.getId());
        }
        return result;
    }

    @Override
    public Object query(Person person, int page, int size) {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            if (person.getName() != null) {
//                boolBuilder.must(QueryBuilders.matchQuery("name", person.getName()));
                boolBuilder.should(QueryBuilders.matchQuery("name", person.getName()));
            }
            if (person.getIntroduce() != null) {
//                boolBuilder.must(QueryBuilders.matchQuery("introduce", person.getIntroduce()));
                boolBuilder.should(QueryBuilders.matchQuery("introduce", person.getIntroduce()));
            }
            if (person.getSex() != null) {
                boolBuilder.should(QueryBuilders.matchQuery("sex", person.getSex()));
            }
            //range 查询范围，大于age,小于age+10
            if (person.getAge() != null && person.getAge() > 0) {
                RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
                rangeQuery.from(person.getAge());
                rangeQuery.to(person.getAge() + 10);
                boolBuilder.filter(rangeQuery);
            }
            SearchRequestBuilder builder = transportClient.prepareSearch(index)
                    .setTypes(type)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setQuery(boolBuilder)
                    .setScroll(TimeValue.timeValueMinutes(1))
                    .setTimeout(TimeValue.timeValueMinutes(10))
//                    .setFrom(page)
                    .setSize(size);
            // 高亮
            //HighlightBuilder hBuilder = new HighlightBuilder();
            //hBuilder.preTags("<h2>");
            //hBuilder.postTags("</h2>");
            //hBuilder.field("question");
            //高亮的字段
            //builder.highlighter(hBuilder);
            log.info(String.valueOf(builder));
            SearchResponse response = builder.get();
            SearchHits hits = response.getHits();

//            for (SearchHit searchHit : hits) {
//                Map<String, Object> map = searchHit.getSourceAsMap();
//                String id = searchHit.getId();
//                map.put("id", id);
//                result.add(map);
//            }
            hits.forEach((s) -> {
                Map<String, Object> sourceAsMap = s.getSourceAsMap();
                sourceAsMap.put("id", s.getId());
                result.add(sourceAsMap);
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return result;
    }
}
