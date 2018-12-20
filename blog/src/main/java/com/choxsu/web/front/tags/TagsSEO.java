package com.choxsu.web.front.tags;

import com.choxsu.common.interceptor.BaseSeoInterceptor;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class TagsSEO extends BaseSeoInterceptor {

    @Override
    public void indexSeo(Controller c) {
    }

    @Override
    public void detailSeo(Controller c) {
        Page<Record> page = c.getAttr("page");
        StringBuilder title = new StringBuilder("Chosu");
        String tagName = null;
        if (page != null && page.getList() != null && page.getList().size() > 0) {
            tagName = page.getList().get(0).getStr("tagName");
            title.append(" | Tag | " + tagName);
        } else {
            title.append(" | Java Developer");
        }
        setSeoTitle(c, title.toString());
        setSeoKeywords(c, "Chosu,Java,JFinal,前端,HTML,后台,后端,数据库,mysql,vue,nodeJs," + tagName);
        setSeoDescr(c, "Chosu极简博客,技术文章分享,带你学会更多的Java技术");
    }

    @Override
    public void othersSeo(Controller c, String method) {

    }
}
