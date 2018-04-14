package com.choxsu.elastic.service;

import com.choxsu.elastic.entity.Article;
import com.choxsu.elastic.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author choxsu
 */
public interface IArticleService {
    Article save(Article article);

    void delete(String id);

    Article findOne(String id);

    Page<Article> findAll();

    Page<Article> findByAuthor(Author author, PageRequest pageRequest);

    Page<Article> findByTitle(String title, PageRequest pageRequest);

    List<Article> findByTitle(String title);

    Iterable<Article> search(String queryString);

}
