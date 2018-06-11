package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: permission()
 */
@Service
public class PermissionService {

    private static final Permission permissionDao = new Permission().dao();

    /**
     * 保存
     */
    public boolean save(Permission permission){
        return permission.save();
    }
    /**
     * 更新
     */
    public boolean update(Permission permission){
        return permission.update();
    }

     /**
      * 通过主键查找
      */
    public Permission findOne(Integer id){
        return permissionDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return permissionDao.deleteById(id);
    }


}