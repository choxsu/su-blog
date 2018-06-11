package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: auth_code()
 */
@Service
public class AuthCodeService {

    private static final AuthCode authCodeDao = new AuthCode().dao();

    /**
     * 保存
     */
    public boolean save(AuthCode authCode){
        return authCode.save();
    }
    /**
     * 更新
     */
    public boolean update(AuthCode authCode){
        return authCode.update();
    }

     /**
      * 通过主键查找
      */
    public AuthCode findOne(Integer id){
        return authCodeDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return authCodeDao.deleteById(id);
    }


}