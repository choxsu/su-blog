package com.choxsu.elastic.service;

import com.choxsu.elastic.dao.StoreDao;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 15:10
 */
@Service
public class StoreService {

    @Autowired
    private StoreDao storeDao;

    @Before(Tx.class)
    public String save(){
        Record record = new Record();
        final String[] id = new String[1];
        Db.tx(() -> {
            record.set("address","九龙坡区谢家湾正街55号华润中心万象城L5层(谢家湾轻轨站旁)");
            record.set("name","大食代美食广场(万象城店)");
            record.set("telephone","(023)68086707");
            Db.save("store", record);
            id[0] = storeDao.save(record);
            return true;
        });

        return id[0];
    }

    public Object find(String id){

        return storeDao.find(id);
    }
}
