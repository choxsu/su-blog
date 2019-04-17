package com.choxsu.front.article;

import com.choxsu.common.interceptor.BaseSeoInterceptor;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class ArticleSEO extends BaseSeoInterceptor {

    @Override
    public void indexSeo(Controller c) {
        setSeoTitle(c, "Choxsu | Java Developer");
        setSeoKeywords(c, "Choxsu,Java,JFinal,前端,HTML,后台,后端,数据库,mysql,vue,nodeJs");
        setSeoDescr(c, "Choxsu极简博客,技术文章分享,带你学会更多的Java技术");
    }

    @Override
    public void detailSeo(Controller c) {
        Record record = c.getAttr("blog");
        String title = record.get("title");
        setSeoTitle(c, title);
        setSeoKeywords(c, title);
        setSeoDescr(c, title);
    }

    @Override
    public void othersSeo(Controller c, String method) {

    }
}
