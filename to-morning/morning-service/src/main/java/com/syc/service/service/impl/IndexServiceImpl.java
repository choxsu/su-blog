package com.syc.service.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.syc.model.entity.jf.Blog;
import com.syc.service.common.CommonService;
import com.syc.service.service.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("indexService")
public class IndexServiceImpl extends CommonService implements IndexService  {


    @Override
    public Page<Blog> findList(Blog blog, int pageNumber, int paeSize) {
        return new Page<>();
    }

    @Override
    public List<Blog> findList(Blog blog, int limit) {
        return null;
    }

    @Override
    public Blog findById(Integer id) {
        return null;
    }

    @Override
    public boolean save(Blog blog) {
        return false;
    }

    @Override
    public boolean update(Blog blog) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
