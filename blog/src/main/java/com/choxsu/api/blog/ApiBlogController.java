package com.choxsu.api.blog;

import com.choxsu.api.vo.BlogListVo;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.choxsu.web.front.index.ArticleService;
import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author chox su
 * @date 2018/03/02 10:22
 */
@Slf4j
public class ApiBlogController extends BaseController {

    @Inject
    ArticleService articleService;

    /**
     * 首页博客list
     */
    public void list() {
        Integer page = getParaToInt("page", 1);
        Integer size = getParaToInt("size", 20);
        Page<Record> pageResult = articleService.findArticles(page, size, null);
        renderJson(success(pageResult.getList()));
    }

    /**
     * 博客detail
     */
    public void index() {
        Record blog = articleService.findBlog(getParaToInt());
        if (blog == null){
            renderJson(success());
            return;
        }
        renderJson(success(blog));
    }


}
