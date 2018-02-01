package com.choxsu.elastic.service;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chox su
 * @date 2017/12/23 17:16
 */
@Service
public class CommonService {

    public Map FindByIdCount(LinkedHashMap<String, Object> params) {
        return Db.findFirst("select count(0) as total from store").getColumns();
    }

    public List<Map> FindByIdList(LinkedHashMap<String, Object> params) {
        List<Record> records = Db.find("select s.id c0, s.uid c1, s.address c2, s.name c3, s.telephone c4, s.detail c5, s.street_id c6  from store s limit 15");
        List<Map> mapList = new ArrayList<>();
        records.forEach(record -> mapList.add(record.getColumns()));
        return mapList;
    }

    public static void main(String[] args) {
        final List list = new ArrayList();
        for (int i = 1; i <= 5000; i++) {
            list.add(i);
        }
        System.out.println("list.size():" + list.size());
        long begin = System.currentTimeMillis();
        System.out.println("开始：" + begin);

//        list.forEach(o -> System.out.println(o));
//        for (Object o : list) {
//            System.out.println(o);
//        }
        for (int i = 0,length = list.size(); i < length; i++) {
            System.out.println(list.get(i));
        }

        long end = System.currentTimeMillis();
        System.out.println("结束：" + end);
        long use = end - begin;
        System.out.println("用时：" + use + "ms");

        new Thread().start();
    }
}
