package com.choxsu.admin.code;

import com.choxsu.admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.CodeConfig;
import com.jfinal.aop.Inject;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Ret;

public class CodeConfigController extends BaseController {

    @Inject
    CodeConfigService ccs;

    @Remark("生成配置")
    public void index(){
        CodeConfig codeConfig = ccs.findById(1L);
        set("codeConfig", codeConfig);
        render("index_config.html");
    }

    @Remark("更新生成配置")
    public void update(@Para("") CodeConfig codeConfig){
        Ret ret = ccs.updateData(codeConfig);
        renderJson(ret);
    }

}
