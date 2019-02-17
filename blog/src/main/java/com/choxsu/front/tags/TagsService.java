package com.choxsu.front.tags;

import com.choxsu.front.article.ArticleService;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author chox su
 * @date 2018/01/06 16:48
 */
public class TagsService {

    @Inject
    ArticleService articleService;// = Enhancer.enhance(ArticleService.class);
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
        return articleService.findArticles(pageNumber, pageSize, tagId);
    }
}
