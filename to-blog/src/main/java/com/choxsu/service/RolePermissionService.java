package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: role_permission()
 */
@Service
public class RolePermissionService {

    private static final RolePermission rolePermissionDao = new RolePermission().dao();

    /**
     * 保存
     */
    public boolean save(RolePermission rolePermission){
        return rolePermission.save();
    }
    /**
     * 更新
     */
    public boolean update(RolePermission rolePermission){
        return rolePermission.update();
    }

     /**
      * 通过主键查找
      */
    public RolePermission findOne(Integer id){
        return rolePermissionDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return rolePermissionDao.deleteById(id);
    }


}