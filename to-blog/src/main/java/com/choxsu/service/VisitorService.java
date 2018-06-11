package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: visitor()
 */
@Service
public class VisitorService {

    private static final Visitor visitorDao = new Visitor().dao();

    /**
     * 保存
     */
    public boolean save(Visitor visitor){
        return visitor.save();
    }
    /**
     * 更新
     */
    public boolean update(Visitor visitor){
        return visitor.update();
    }

     /**
      * 通过主键查找
      */
    public Visitor findOne(Integer id){
        return visitorDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return visitorDao.deleteById(id);
    }


}