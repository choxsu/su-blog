package com.choxsu._admin.quartz;


import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * @author choxsu
 * @date 2019/01/05
 */
public class JobManageService {


    public Page<Record> getJobList(int page, int size) {
        return Db.paginate(page, size, "select * ", " from job_manager order by id desc");
    }


    public Ret saveOrUpdate(Record r) {
        if (r == null) {
            return Ret.fail().set("msg", "参数必须传递");
        }
        if (StrKit.notBlank(r.getStr("id"))){
            Db.update("job_manager", r);
            return Ret.ok().set("msg", "保存成功");
        }else {
            r.set("is_enabled", "N");
            Db.save("job_manager", r);
            return Ret.ok().set("msg", "保存成功");
        }
    }

    public Record findById(Integer id) {
        return Db.findById("job_manager", id);
    }
}
