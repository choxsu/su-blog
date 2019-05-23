package com.choxsu._admin.quartz;

import com.choxsu._admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.quartz.QuartzManager;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.quartz.Job;

public class JobManageController extends BaseController {


    @Inject
    JobManageService jobManageService;

    //获取任务信息
    @Remark("定时任务管理首页")
    public void index() {
        int page = getParaToInt("p", 1);
        int size = getParaToInt("size", 10);
        Page<Record> pageRecord = jobManageService.getJobList(page, size);
        setAttr("page", pageRecord);
        render("index.html");
    }
    @Remark("定时任务添加页面")
    public void add() {
        render("addOrEdit.html");
    }

    //新增任务
    @Remark("定时任务保存")
    @Before(JobManageValid.class)
    public void save() {
        String name = getPara("name");
        String group = getPara("group");
        String clazz = getPara("clazz");
        String cron_expression = getPara("cron_expression");
        Record r = new Record();
        r.set("name", name);
        r.set("group", group);
        r.set("clazz", clazz);
        r.set("cron_expression", cron_expression);
        Ret ret = jobManageService.saveOrUpdate(r);
        renderJson(ret);
    }
    @Remark("定时任务编辑页面")
    public void edit() {
        Record record = jobManageService.findById(getParaToInt("id"));
        setAttr("job", record);
        render("addOrEdit.html");
    }

    /**
     * 更新任务
     */
    @Remark("定时任务更新")
    @Before(JobManageValid.class)
    public void update() {
        String id = getPara("id");
        String name = getPara("name");
        String group = getPara("group");
        String clazz = getPara("clazz");
        String cron_expression = getPara("cron_expression");
        Record r = new Record();
        r.set("name", name);
        r.set("group", group);
        r.set("clazz", clazz);
        r.set("cron_expression", cron_expression);
        r.set("id", id);
        Ret ret = jobManageService.saveOrUpdate(r);
        renderJson(ret);
    }
    @Remark("定时任务删除")
    public void delete() {
        Integer id = getParaToInt("id");
        Ret ret = jobManageService.delete(id);
        renderJson(ret);
    }
    //启用关停
    @Remark("定时任务开始")
    public void start() {
        try {
            final String id = getPara("id");
            final Record r = Db.findById("job_manager", id);
            if (r != null) {
                //事务管理，确保数据库状态与任务管理器一致
                boolean bl = Db.tx(() -> {
                    String name = r.getStr("name");
                    String group = r.getStr("group");
                    String clazz = r.getStr("clazz");
                    String cron_expression = r.getStr("cron_expression");
                    String is_enabled = r.getStr("is_enabled");
                    String change_enable = "N".equals(is_enabled) ? "Y" : "N";
                    r.set("is_enabled", change_enable);
                    Db.update("job_manager", "id", r);

                    Class<? extends Job> jobClazz;
                    try {
                        jobClazz = Class.forName(clazz).asSubclass(Job.class);
                    } catch (ClassNotFoundException e) {
                        LogKit.error("job类异常:" + e);
                        throw new RuntimeException("job类异常");
                    }

                    QuartzManager qm = new QuartzManager();
                    if ("Y".equals(r.getStr("is_enabled"))) {
                        try {
                            qm.removeJob(r.getStr("name"), r.getStr("group"));
                        } catch (Exception e) {
                            LogKit.error("removeJob_err:" + e);
                        }
                        qm.addJob(r.getStr("name"), r.getStr("group"), jobClazz, r.getStr("cron_expression"));
                    } else {
                        qm.removeJob(r.getStr("name"), r.getStr("group"));
                    }
                    return true;
                });
            }

        } catch (Exception e) {
            LogKit.error("e==" + e);
            renderJson(fail(""));
        }

        renderJson(success());
    }

    @Remark("定时任务详情")
    public void get() {
        Record r = null;
        try {
            String id = getPara("id");
            r = Db.findById("job_manager", id);
        } catch (Exception e) {
            LogKit.error("e==" + e);
            renderJson(fail(e.getMessage()));
        }
        renderJson(success(r));
    }


}
