package com.choxsu._admin.code;

import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.CodeConfig;
import com.jfinal.aop.Inject;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Ret;

public class CodeConfigController extends BaseController {

    @Inject
    CodeConfigService ccs;

    public void index(){
        CodeConfig codeConfig = ccs.findById(1L);
        set("codeConfig", codeConfig);
        render("index_config.html");
    }

    public void update(@Para("") CodeConfig codeConfig){
        Ret ret = ccs.updateData(codeConfig);
        renderJson(ret);
    }

}
