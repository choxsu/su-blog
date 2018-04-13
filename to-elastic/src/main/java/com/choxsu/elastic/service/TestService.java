package com.choxsu.elastic.service;

import com.choxsu.elastic.config.JFinalTx;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;


/**
 * @author choxsu
 */
@Service
public class TestService {

    /**
     * 事物测试
     *
     * @return
     */
    @JFinalTx
    public Object testTran() {
        Record record = new Record();
        record.set("id", 999);
        Db.save("test", record);
        if (true) {
//            throw new RuntimeException("test");
        }
        return Ret.by("msg", "success");
    }
}
