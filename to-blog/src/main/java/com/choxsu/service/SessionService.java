package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: session()
 */
@Service
public class SessionService {

    private static final Session sessionDao = new Session().dao();

    /**
     * 保存
     */
    public boolean save(Session session){
        return session.save();
    }
    /**
     * 更新
     */
    public boolean update(Session session){
        return session.update();
    }

     /**
      * 通过主键查找
      */
    public Session findOne(Integer id){
        return sessionDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return sessionDao.deleteById(id);
    }


}