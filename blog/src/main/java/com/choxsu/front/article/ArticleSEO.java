package com.choxsu.front.article;

import com.choxsu.common.entity.Blog;
import com.choxsu.common.interceptor.BaseSeoInterceptor;
import com.jfinal.core.Controller;

public class ArticleSEO extends BaseSeoInterceptor {

    @Override
    public void indexSeo(Controller c) {
        setSeoTitle(c, "Choxsu博客社区");
        setSeoKeywords(c, "Choxsu,Java,JFinal,前端,HTML,后台,后端,数据库,mysql,vue,nodeJs");
        setSeoDescr(c, "Choxsu博客社区,技术文章分享,带你学会更多的Java技术");
    }

    @Override
    public void detailSeo(Controller c) {
        Blog blog = c.getAttr("blog");
        String title = blog.getTitle();
        setSeoTitle(c, title);
        setSeoKeywords(c, title);
        setSeoDescr(c, title);
    }

    @Override
    public void othersSeo(Controller c, String method) {

    }
}
