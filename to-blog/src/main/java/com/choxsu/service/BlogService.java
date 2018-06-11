package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: blog()
 */
@Service
public class BlogService {

    private static final Blog blogDao = new Blog().dao();

    /**
     * 保存
     */
    public boolean save(Blog blog){
        return blog.save();
    }
    /**
     * 更新
     */
    public boolean update(Blog blog){
        return blog.update();
    }

     /**
      * 通过主键查找
      */
    public Blog findOne(Integer id){
        return blogDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return blogDao.deleteById(id);
    }


}