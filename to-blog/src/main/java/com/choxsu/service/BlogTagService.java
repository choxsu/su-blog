package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: blog_tag(标签表)
 */
@Service
public class BlogTagService {

    private static final BlogTag blogTagDao = new BlogTag().dao();

    /**
     * 保存
     */
    public boolean save(BlogTag blogTag){
        return blogTag.save();
    }
    /**
     * 更新
     */
    public boolean update(BlogTag blogTag){
        return blogTag.update();
    }

     /**
      * 通过主键查找
      */
    public BlogTag findOne(Integer id){
        return blogTagDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return blogTagDao.deleteById(id);
    }


}