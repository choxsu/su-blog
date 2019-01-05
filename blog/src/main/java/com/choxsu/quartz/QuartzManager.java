package com.choxsu.quartz;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzManager {

    private static Scheduler scheduler = null;

    public QuartzManager() {
        try {
            if (scheduler == null){
                scheduler = new StdSchedulerFactory().getScheduler();
            }
            LogKit.info("初始化调度器 ");
        } catch (SchedulerException ex) {
            LogKit.error("初始化调度器=> [失败]:" + ex.getLocalizedMessage());
        }
    }


    //初始化启动任务
    public void initJob() {

        List<Record> jobs = Db.find("SELECT * FROM job_manager WHERE 1=1 AND is_enabled = 'Y'");

        for (Record job : jobs) {
            if ("Y".equals(job.getStr("is_enabled"))) {
                String className = job.getStr("class");
                Class<? extends Job> jobClazz;
                try {
                    jobClazz = Class.forName(className).asSubclass(Job.class);
                } catch (Exception e) {
                    LogKit.error(className + "没有继承job,e==" + e);
                    continue;
                }
                String name = job.getStr("name");
                String group = job.getStr("group");
                String cronExpression = job.getStr("cron_expression");
                this.addJob(name, group, jobClazz, cronExpression);
            }
        }
        this.start();
    }

    //添加任务
    public void addJob(String name, String group, Class<? extends Job> clazz, String cronExpression) {
        try {
            // 构造任务
            JobDetail job = newJob(clazz).withIdentity(name, group).build();
            // 构造任务触发器
            Trigger trg = newTrigger().withIdentity(name, group).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
            // 将作业添加到调度器
            scheduler.scheduleJob(job, trg);
            LogKit.info("创建作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            LogKit.error("创建作业=> [作业名称：" + name + " 作业组：" + group + "]=> [失败]");
        }
    }

    //移除任务
    public void removeJob(String name, String group) {
        try {
            TriggerKey tk = TriggerKey.triggerKey(name, group);
            scheduler.pauseTrigger(tk);// 停止触发器
            scheduler.unscheduleJob(tk);// 移除触发器
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.deleteJob(jobKey);// 删除作业
            LogKit.info("删除作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        } catch (SchedulerException e) {
            e.printStackTrace();
            LogKit.error("删除作业=> [作业名称：" + name + " 作业组：" + group + "]=> [失败]");
        }
    }

    public void start() {
        try {
            if (!scheduler.isStarted()){
                scheduler.start();
                LogKit.info("启动调度器 ");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            LogKit.error("启动调度器=> [失败]");
        }
    }

    public void shutdown() {
        try {
            if (!scheduler.isShutdown()){
                scheduler.shutdown();
                LogKit.info("停止调度器 ");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            LogKit.error("停止调度器=> [失败]");
        }
    }
}
