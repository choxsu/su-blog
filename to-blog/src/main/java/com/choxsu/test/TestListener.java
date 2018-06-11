package com.choxsu.test;

import net.dreamlu.event.core.ApplicationEvent;
import net.dreamlu.event.core.EventListener;

/**
 * @author chox su
 * @date 2018/03/13 11:16
 */
public class TestListener {

    @EventListener(order = 1, events = Test1Event.class, async = true)
    public void test1(ApplicationEvent event) {
        Object xx = event.getSource();
        System.out.println(Thread.currentThread().getName() + " " + this.getClass() + " " + "\tsource:" + xx);
        for (int i = 0; i < 100000; i++) {
            System.out.println("i:"+i);
        }
    }

    @EventListener(order = 2, events = Test1Event.class)
    public void test2(ApplicationEvent event) {
        Object xx = event.getSource();
        System.out.println(Thread.currentThread().getName() + " " + this.getClass() + " " + "\tsource:" + xx);
        System.out.println("test2:发送邮箱确认监听");
    }





}
