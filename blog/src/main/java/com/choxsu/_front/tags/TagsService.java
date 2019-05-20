package com.choxsu._front.tags;

import com.choxsu.common.entity.Blog;
import com.choxsu._front.article.ArticleService;
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
    public Page<Blog> findBlogByTagId(Integer pageNumber, Integer pageSize, Integer tagId) {
        return articleService.findArticles(pageNumber, pageSize, tagId);
    }
}
