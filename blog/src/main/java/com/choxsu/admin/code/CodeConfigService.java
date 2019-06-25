package com.choxsu.admin.code;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.CodeConfig;
import com.jfinal.kit.Ret;

import java.util.Date;

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
