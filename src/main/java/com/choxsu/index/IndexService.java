package com.choxsu.index;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @author choxsu
 */
public class IndexService {

    public List<Record> findBlogTags(){
        return Db.find("SELECT id,name FROM blog_tag WHERE status = ?", 0);
    }
}
