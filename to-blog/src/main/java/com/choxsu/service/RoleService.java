package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: role()
 */
@Service
public class RoleService {

    private static final Role roleDao = new Role().dao();

    /**
     * 保存
     */
    public boolean save(Role role){
        return role.save();
    }
    /**
     * 更新
     */
    public boolean update(Role role){
        return role.update();
    }

     /**
      * 通过主键查找
      */
    public Role findOne(Integer id){
        return roleDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return roleDao.deleteById(id);
    }


}