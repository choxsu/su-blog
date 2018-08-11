package com.choxsu.web.front.tags;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.web.front.index.IndexService;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:48
 */
public class TagsService {

    IndexService indexService = Enhancer.enhance(IndexService.class);
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
        sb.append("FROM blog where isDelete = ? and tag_id = ? and category != ?  ORDER BY clickCount DESC,updateAt DESC,createAt DESC");

        String from = sb.toString();
        Page<Record> page = Db.paginate(pageNumber, pageSize, select, from, 0, tagId, CategoryEnum.ABOUT.getName());
        indexService.filedHandle(page);
        return page;
    }
}
