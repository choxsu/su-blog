package com.choxsu.front.article;

import com.choxsu.common.entity.BlogReply;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author choxsu
 */
public class ArticleReplyService {

    private BlogReply blogReplyDao = new BlogReply().dao();

    Page<BlogReply> getReplyPage(Integer articleId, Integer pageNumber) {
        return blogReplyDao.template("article.getReplyPage", articleId).paginate(pageNumber, 10);
    }
}
