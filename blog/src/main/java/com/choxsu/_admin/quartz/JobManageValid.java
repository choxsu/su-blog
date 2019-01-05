package com.choxsu._admin.quartz;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * @author choxsu
 * @date 2019/01/05
 */
public class JobManageValid extends Validator {
    @Override
    protected void validate(Controller c) {
        setShortCircuit(true);
        validateRequired("name", "msg", "任务名必须填写");
        validateRequired("group", "msg", "组名必须填写");
        validateRequired("clazz", "msg", "类名必须填写");
        validateRequired("cron_expression", "msg", "定时表达式必须填写");
    }

    @Override
    protected void handleError(Controller c) {
        c.renderJson();
    }
}
