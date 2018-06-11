package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: account()
 */
@Service
public class AccountService {

    private static final Account accountDao = new Account().dao();

    /**
     * 保存
     */
    public boolean save(Account account){
        return account.save();
    }
    /**
     * 更新
     */
    public boolean update(Account account){
        return account.update();
    }

     /**
      * 通过主键查找
      */
    public Account findOne(Integer id){
        return accountDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return accountDao.deleteById(id);
    }


}