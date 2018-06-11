package com.choxsu.service;

import org.springframework.stereotype.Service;
import com.choxsu.common.entity.*;

/**
 * @author chox su
 * 表: blog_category(类别表)
 */
@Service
public class BlogCategoryService {

    private static final BlogCategory blogCategoryDao = new BlogCategory().dao();

    /**
     * 保存
     */
    public boolean save(BlogCategory blogCategory){
        return blogCategory.save();
    }
    /**
     * 更新
     */
    public boolean update(BlogCategory blogCategory){
        return blogCategory.update();
    }

     /**
      * 通过主键查找
      */
    public BlogCategory findOne(Integer id){
        return blogCategoryDao.findById(id);
    }

     /**
      * 通过主键删除
      */
    private boolean deleteById(Integer id){
        return blogCategoryDao.deleteById(id);
    }


}