package com.choxsu.api.blog;

import com.choxsu.front.article.ArticleService;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.Blog;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Page;
import lombok.extern.slf4j.Slf4j;

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
    public void list(Integer page, Integer size) {
        page = getDefaultInt(page, 1);
        size = getDefaultInt(size, 10);
        Page<Blog> pageResult = articleService.findArticles(page, size, null);
        renderJson(success(pageResult.getList()));
    }

    /**
     * 博客detail
     */
    public void index() {
        Blog blog = articleService.findBlog(getParaToInt());
        if (blog == null){
            renderJson(success());
            return;
        }
        renderJson(success(blog));
    }


}
