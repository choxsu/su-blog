package com.choxsu.test;

import com.choxsu.common.base.BaseController;
import net.dreamlu.event.EventKit;

/**
 * @author chox su
 * @date 2018/03/09 10:05
 */
public class TestController extends BaseController {

    public void index() {
        long begin = System.currentTimeMillis();
        //发送事件
        EventKit.post(new Test1Event("hello1"));

        System.out.println("用时："+(System.currentTimeMillis() - begin) + "ms");



        renderJson(respSuccess("事件发送成功"));
    }
}
