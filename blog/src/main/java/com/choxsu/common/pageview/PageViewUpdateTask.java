package com.choxsu.common.pageview;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.cron4j.ITask;


/**
 * @author choxsu
 * @date 2018/12/20
 */
public class PageViewUpdateTask implements ITask {

    PageViewService pageViewService = Aop.get(PageViewService.class);

    @Override
    public void stop() {
        this.doUpdate();
    }

    @Override
    public void run() {
        this.doUpdate();
    }

    private void doUpdate() {
        pageViewService.updateToDataBase();
        pageViewService.updateArticleClickToDataBase();
    }
}
