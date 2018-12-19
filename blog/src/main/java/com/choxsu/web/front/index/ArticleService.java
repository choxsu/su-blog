package com.choxsu.web.front.index;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * @author choxsu
 */
public class ArticleService {

    public static void addClick(Object id) {
        Db.update("UPDATE blog set clickCount = clickCount + 1 WHERE id = ?", id);
    }

    /**
     * 查询所有博客分页
     * TODO update: 最近两天发布的放在最前面
     *
     * @param page
     * @return
     */
    public Page<Record> findArticles(int page, int pageSize, Integer tagId) {
        Kv kv = Kv.create().set("tagId", tagId);
        SqlPara sqlPara = Db.getSqlPara("article.list", kv);
        return Db.paginate(page, pageSize, sqlPara);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    public Record findBlog(Integer id) {
        Record blog = Db.findById("blog", id);
        if (blog == null){
            return null;
        }
        this.doTagNameSet(blog);
        return blog;
    }

    /**
     * 封装tag名称
     *
     * @param s
     */
    public void doTagNameSet(Record s) {
        assert s != null;
        Record record = Db.findById("blog_tag", s.get("tag_id"));
        if (record == null) return;
        String name = record.getStr("name");
        s.set("tagName", name);
    }
}
