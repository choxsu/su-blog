package com.choxsu._admin.code;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.CodeConfig;
import com.choxsu.utils.kit.SnowFlakeKit;
import com.choxsu.utils.kit.ZipFilesKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipOutputStream;

public class CodeConfigService extends BaseService<CodeConfig> {

    public CodeConfig findById(Long id) {
        return DAO.findById(id);
    }


    public Ret updateData(CodeConfig codeConfig) {
        Long id = codeConfig.getId();
        if (id == null) {
            codeConfig.setId(1L);
            codeConfig.setCreateTime(new Date());
            codeConfig.setUpdateTime(new Date());
            boolean save = codeConfig.save();
            if (save) {
                return Ret.ok().set("msg", "更新成功");
            }
        }
        codeConfig.setUpdateTime(new Date());
        boolean update = codeConfig.update();
        if (update) {
            return Ret.ok().set("msg", "更新成功");
        }
        return Ret.fail().set("msg", "更新失败");
    }
}
