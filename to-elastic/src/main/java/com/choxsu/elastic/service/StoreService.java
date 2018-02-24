package com.choxsu.elastic.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 15:10
 */
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
}
