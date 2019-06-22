package com.choxsu._admin.code;

import com.choxsu._admin.permission.Remark;
import com.choxsu.common.base.BaseController;
import com.choxsu.common.entity.CodeConfig;
import com.choxsu.utils.kit.ZipFilesKit;
import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeController extends BaseController {

    @Inject
    CodeService codeService;

    @Remark("代码生成首页")
    public void index() {
        int p = getInt("p", 1);
        String tableName = get("name");
        String remark = get("remark");
        Page<Record> tables = codeService.getTables(p, 10, tableName, remark);
        set("pageResult", tables);
        set("name", tableName);
        set("remark", remark);
        render("index.html");
    }

    @Remark("代码生成")
    public void gen() {
        Boolean isAll = getBoolean("isAll", false);
        List<String> list = new ArrayList<>();
        if (isAll) {
            List<Record> allTables = codeService.getAllTables();
            for (Record record : allTables) {
                list.add(record.getStr("tableName"));
            }
        } else {
            String tables = get("tables");
            if (StrKit.notBlank(tables)) {
                String[] tablesArr = tables.split(",");
                list = Arrays.asList(tablesArr);
            }
        }
        List<CodeConfig> codeConfigs = new ArrayList<>();
        Ret ret = codeService.genValid(list, codeConfigs);
        if (ret != null) {
            renderJson(ret);
            return;
        }
        File zipFile = codeService.gen(list, codeConfigs.get(0));
        if (zipFile == null) {
            throw new RuntimeException("文件不存在");
        }
        HttpServletResponse response = getResponse();
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + zipFile.getName());
        try {
            ServletOutputStream out = response.getOutputStream();
            FileInputStream input = new FileInputStream(zipFile);
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = input.read(buffer);
                //写到输出流(out)中
                out.write(buffer, 0, b);
            }
            input.close();
            out.flush();
            out.close();
            ZipFilesKit.deleteDir(zipFile);
            renderNull();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
