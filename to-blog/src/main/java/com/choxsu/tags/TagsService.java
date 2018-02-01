package com.choxsu.tags;

import com.choxsu.index.IndexService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:48
 */
public class TagsService {

    private static IndexService indexService = IndexService.me;
    /**
     * 查询通过id
     * @param tagId
     * @return
     */
    public Record findById(Integer tagId) {
        return Db.findById("blog_tag", tagId);
    }

    /**
     * 获取 不同类型的博客
     *
     * @param pageNumber   当前页
     * @param pageSize     每页条数
     * @return
     */
    public Page<Record> findBlogByTagId(Integer pageNumber,Integer pageSize, Integer tagId) {

        assert tagId != null && tagId > 0;

        String select = "SELECT id,title,content,createAt,updateAt,clickCount,category,tag_id as tagId,category_id as categoryId ";

        StringBuilder sb = new StringBuilder();
        sb.append("FROM blog where isDelete = ? and tag_id = ? ORDER BY clickCount DESC,updateAt DESC,createAt DESC");

        String from = sb.toString();
        Page<Record> page = Db.paginate(pageNumber, pageSize, select, from, 0, tagId);
        indexService.filedHandle(page);
        return page;
    }
}
