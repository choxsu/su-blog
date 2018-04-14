package com.choxsu.elastic.repository;

import com.choxsu.elastic.entity.Article;
import com.choxsu.elastic.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author choxsu
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {

    Page<Article> findByAuthor(Author author, Pageable pageable);

    List<Article> findByTitle(String title);

    Page<Article> findByTitle(String title, Pageable pageable);

}
