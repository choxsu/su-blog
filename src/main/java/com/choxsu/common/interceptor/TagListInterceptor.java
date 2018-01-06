package com.choxsu.common.interceptor;

import com.choxsu.index.IndexService;
import com.jfinal.aop.Enhancer;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * @author chox su
 * @date 2018/01/06 12:57
 */
public class TagListInterceptor implements Interceptor {

    private static final IndexService indexService = Enhancer.enhance(IndexService.class);


    @Override
    public void intercept(Invocation inv) {
        Controller c = inv.getController();
        inv.invoke();

        List<Record> tags = indexService.findBlogTags();
        indexService.addTopicNum(tags);
        c.setAttr("tags", tags);
    }
}
