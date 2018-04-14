package com.choxsu.elastic.controller;

import com.choxsu.elastic.entity.Article;
import com.choxsu.elastic.entity.Author;
import com.choxsu.elastic.service.IArticleService;
import com.jfinal.kit.Ret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author choxsu
 */
@RestController
@RequestMapping(value = {"/api/article/v1"})
public class ArticleController {

    @Autowired
    @Qualifier(value = "articleServiceImpl")
    private IArticleService articleService;


    @PostMapping(value = "/save")
    public Object save(Article article) {
        articleService.save(article);
        return Ret.create("msg", "success").set("data", article);
    }

    @GetMapping(value = "/delete")
    public Object delete(String id) {

        articleService.delete(id);
        return Ret.create("msg", "success");
    }

    @GetMapping(value = "/findOne")
    public Object findOne(String id) {

        Article article = articleService.findOne(id);
        return Ret.create("msg", "success").set("data", article);
    }

    @GetMapping(value = "/findAll")
    public Object findAll() {

        Page<Article> articles = articleService.findAll();
        List<Article> content = articles.getContent();
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);
        map.put("pageNumber", articles.getNumber());
        map.put("pageSize", articles.getSize());
        map.put("total", articles.getTotalElements());
        map.put("totalPage", articles.getTotalPages());
        map.put("sort", articles.getSort());

        return Ret.create("msg", "success").set("data", map).set("err_code", 1);
    }

    @GetMapping(value = "/findByAuthor")
    public Object findByAuthor(Author author, PageRequest pageRequest) {


        Page<Article> page = articleService.findByAuthor(author, pageRequest);
        return Ret.create("msg", "success").set("data", page);
    }


    @GetMapping(value = "/findByTitlePage")
    public Object findByTitle(String title, PageRequest pageRequest) {

        Page<Article> all = articleService.findByTitle(title, pageRequest);
        return Ret.create("msg", "success").set("data", all);
    }

    @GetMapping(value = "/findByTitle")
    public Object findByTitle(String title) {

        List<Article> list = articleService.findByTitle(title);
        return Ret.create("msg", "success").set("data", list);
    }

    @GetMapping(value = "/search")
    public Object search(String queryString) {
        Iterable<Article> search = articleService.search(queryString);
        return Ret.create("msg", "success").set("data", search);
    }

}
