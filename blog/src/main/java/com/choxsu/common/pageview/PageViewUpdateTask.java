package com.choxsu.common.pageview;

import com.jfinal.aop.Aop;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.cron4j.ITask;


/**
 * @author choxsu
 * @date 2018/12/20
 */
public class PageViewUpdateTask implements ITask {

    //这里不能使用 @Inject ,只能使用Aop.get(XXX.class)来注入
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
        // 如果是分布式环境下面，可以在这里加上分布式锁，防止数据的错误
        pageViewService.updateToDataBase();
    }
}
