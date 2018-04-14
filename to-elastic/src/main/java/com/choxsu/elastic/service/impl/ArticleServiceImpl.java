package com.choxsu.elastic.service.impl;

import com.choxsu.elastic.entity.Article;
import com.choxsu.elastic.entity.Author;
import com.choxsu.elastic.repository.ArticleRepository;
import com.choxsu.elastic.service.IArticleService;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author choxsu
 */
@Service("articleServiceImpl")
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article save(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setPostTime(new Date());
        article.setClickCount(0L);
        return articleRepository.save(article);
    }

    @Override
    public void delete(String id) {
        articleRepository.delete(id);
    }

    @Override
    public Article findOne(String id) {
        return articleRepository.findOne(id);
    }

    @Override
    public Page<Article> findAll() {
        return (Page<Article>) articleRepository.findAll();
    }

    @Override
    public Page<Article> findByAuthor(Author author, PageRequest pageRequest) {
        return articleRepository.findByAuthor(author, pageRequest);
    }

    @Override
    public Page<Article> findByTitle(String title, PageRequest pageRequest) {
        return articleRepository.findByTitle(title, pageRequest);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Override
    public Iterable<Article> search(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        return articleRepository.search(builder);
    }
}
