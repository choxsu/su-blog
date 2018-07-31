package com.choxsu.web.front.search;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.web.front.index.IndexService;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chox su
 * @date 2018/01/06 16:44
 */
public class SearchService {

    @Inject
    IndexService indexService;

    public Page<Record> search(Integer pageNumber, Integer pageSize, String keyword) {
        if (StrKit.isBlank(keyword)) {
            return new Page<>();
        }
        Kv kv = Kv.create();
        kv.put("keyword", keyword);
        int start = (pageNumber - 1) * pageSize;
        kv.put("category", CategoryEnum.ABOUT.getName());
        kv.put("start", start);
        kv.put("pageSize", pageSize);
        SqlPara sqlPara = Db.getSqlPara("blog.searchCount", kv);
        Record record = Db.findFirst(sqlPara);
        Long t = record.getLong("t");
        List<Record> list;
        if (t > 0) {
            sqlPara = Db.getSqlPara("blog.searchList", kv);
            list = Db.find(sqlPara);
        } else {
            list = new ArrayList<>();
        }

        Page<Record> page = new Page<Record>(list, pageNumber, pageSize, (int) (t / pageSize), t.intValue());
        indexService.filedHandle(page);
        return page;
    }
}
