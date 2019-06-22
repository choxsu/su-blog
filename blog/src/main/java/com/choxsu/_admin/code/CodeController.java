package com.choxsu._admin.code;

import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CodeController extends BaseController {

    @Inject
    CodeService codeService;

    public void index() throws Exception {
        int p = getInt("p", 1);
        Page<Record> tables = codeService.getTables(p, 10);
        set("pageResult", tables);
        render("index.html");
    }

}
