package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: account_role()
 */
@Service
public class AccountRoleService {

    private static final AccountRole accountRoleDao = new AccountRole().dao();

    /**
     * 保存
     */
    public boolean save(AccountRole accountRole){
        return accountRole.save();
    }
    /**
     * 更新
     */
    public boolean update(AccountRole accountRole){
        return accountRole.update();
    }

     /**
      * 通过主键查找
      */
    public AccountRole findOne(Integer id){
        return accountRoleDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return accountRoleDao.deleteById(id);
    }


}