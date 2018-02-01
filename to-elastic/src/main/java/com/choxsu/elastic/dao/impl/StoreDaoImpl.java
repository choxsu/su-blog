package com.choxsu.elastic.dao.impl;

import com.choxsu.elastic.dao.StoreDao;
import com.choxsu.elastic.entity.Person;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 16:01
 */
@Component
public class StoreDaoImpl implements StoreDao {

    @Autowired
    private TransportClient transportClient;

    private Logger log = Logger.getLogger(getClass());

    /**
     * 索引名称（数据库名）
     */
    private String index = "jfinal_club";
    /**
     * 类型名称（表名）
     */
    private String type = "store";


    @Override
    public String save(Record record) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            Map<String, Object> columns = record.getColumns();
            record.getColumns().keySet().forEach(key ->{
                try {
                    builder.field(key,columns.get(key));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            });
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
    public String update(Record record) {
        return null;
    }

    @Override
    public String deltele(String id) {
        return null;
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
    public Object query(Record record, int page, int size) {
        return null;
    }
}
