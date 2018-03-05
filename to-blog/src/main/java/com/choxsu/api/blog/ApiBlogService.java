package com.choxsu.api.blog;

import com.choxsu.api.entity.Author;
import com.choxsu.api.vo.BlogListVo;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogTag;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chox su
 * @date 2018/03/02 10:22
 */
@Before(Tx.class)
public class ApiBlogService {

    private static final Blog blogDao = new Blog().dao();
    private static final BlogTag blogTabDao = new BlogTag().dao();

    /**
     * 首页博客list
     *
     * @param tab   分类tab
     * @param page  当前页
     * @param size  每页条数
     */
    public List<BlogListVo> list(String tab, Integer page, Integer size) {
        List<BlogListVo> blogListVos = new ArrayList<>();
        SqlPara sqlPara = blogDao.getSqlPara("blog.paginate");
        Page<Blog> projectPage = blogDao.paginate(page, size, sqlPara);
        List<Blog> list = projectPage.getList();
        BlogListVo blogListVo = null;
        Author author = null;
        for (Blog blog : list) {
            blogListVo = new BlogListVo();
            blogListVo.setAuthor_id(1);
            blogListVo.setContent(blog.getContent());
            blogListVo.setCreate_at(blog.getCreateAt());
            blogListVo.setGood(true);
            blogListVo.setId(blog.getId());
            blogListVo.setLast_reply_at(blog.getUpdateAt());
            blogListVo.setTab(getTabName(blog.getTagId()));
            blogListVo.setTitle(blog.getTitle());
            blogListVo.setTop(true);
            blogListVo.setVisit_count(blog.getClickCount());
            blogListVo.setReply_count(0);

            author = new Author();
            author.setAvatar_url("https://avatars1.githubusercontent.com/u/227713?v=3&s=120");
            author.setLoginname("ChoxSu");
            blogListVo.setAuthor(author);
            blogListVos.add(blogListVo);
        }
        return blogListVos;
    }

    /**
     * 获取标签名字
     * @param tabId 标签id
     * @return String 默认返回blog
     */
    private synchronized String getTabName(Integer tabId){
        if (tabId == null){
            return Blog.defaultTag;
        }
        BlogTag tag = blogTabDao.findById(tabId);
        if (tag == null){
            return Blog.defaultTag;
        }
        return tag.getName();
    }

}
