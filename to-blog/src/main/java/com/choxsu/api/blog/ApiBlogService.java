package com.choxsu.api.blog;

import com.choxsu.api.vo.AuthorVo;
import com.choxsu.api.vo.BlogDetailVo;
import com.choxsu.api.vo.BlogListVo;
import com.choxsu.api.vo.RepliesVo;
import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.entity.BlogTag;
import com.choxsu.web.front.index.ArticleService;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
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
public class ApiBlogService extends BaseService<Blog> {

    private static final Blog blogDao = new Blog().dao();
    private static final BlogTag blogTabDao = new BlogTag().dao();

    private static final String headerImgUrl = "https://blog.styg.site/images/100.jpg";
    private static final String authorName = "ChoxSu";
    /**
     * 首页博客list
     *
     * @param tab  分类tab
     * @param page 当前页
     * @param size 每页条数
     */
    public List<BlogListVo> list(String tab, Integer page, Integer size) {
        List<BlogListVo> blogListVos = new ArrayList<>();
        Kv kv = Kv.create();
        if (!StrKit.isBlank(tab) && !tab.equals("all")) {
            kv.set("category = ", tab);
        }
        SqlPara sqlPara = DAO.getSqlPara("blog.paginate", Kv.by("cond", kv));
        Page<Blog> projectPage = DAO.paginate(page, size, sqlPara);
        List<Blog> list = projectPage.getList();
        BlogListVo blogListVo;
        AuthorVo authorVo;
        boolean top = false;
        for (Blog blog : list) {
            blogListVo = new BlogListVo();
            blogListVo.setAuthor_id(1);
            blogListVo.setContent(blog.getContent());
            blogListVo.setCreate_at(blog.getCreateAt());
            blogListVo.setGood(true);
            blogListVo.setId(blog.getId());
            blogListVo.setLast_reply_at(blog.getUpdateAt());
            blogListVo.setTab(blog.getCategory());
            blogListVo.setTitle(blog.getTitle());
            if (getTabName(blog.getTagId()).equals(Blog.defaultTag)){
                top = true;
            }
            blogListVo.setTop(top);
            blogListVo.setVisit_count(blog.getClickCount());
            blogListVo.setReply_count(0);

            authorVo = new AuthorVo();
            authorVo.setAvatar_url(headerImgUrl);
            authorVo.setLoginname(authorName);
            blogListVo.setAuthor(authorVo);
            blogListVos.add(blogListVo);
        }
        return blogListVos;
    }

    /**
     * 获取标签名字
     *
     * @param tabId 标签id
     * @return String 默认返回Java
     */
    private synchronized String getTabName(Integer tabId) {
        if (tabId == null) {
            return Blog.defaultTag;
        }
        BlogTag tag = blogTabDao.findById(tabId);
        if (tag == null) {
            return Blog.defaultTag;
        }
        return tag.getName();
    }

    /**
     * 详情
     *
     * @param id 博客id
     * @return BlogListVo
     */
    public BlogListVo detail(Integer id) {
        BlogDetailVo blogDetailVo = new BlogDetailVo();
        boolean top = false;
        Blog blog = blogDao.findById(id);
        if (blog == null){
            return null;
        }
        //继承父类
        blogDetailVo.setAuthor_id(1);
        blogDetailVo.setContent(blog.getContent());
        blogDetailVo.setCreate_at(blog.getCreateAt());
        blogDetailVo.setGood(true);
        blogDetailVo.setId(blog.getId());
        blogDetailVo.setLast_reply_at(blog.getUpdateAt());
        blogDetailVo.setTab(blog.getCategory());
        blogDetailVo.setTitle(blog.getTitle());
        if (getTabName(blog.getTagId()).equals(Blog.defaultTag)){
            top = true;
        }
        blogDetailVo.setTop(top);
        blogDetailVo.setVisit_count(blog.getClickCount());
        blogDetailVo.setReply_count(0);

        AuthorVo authorVo = new AuthorVo();
        authorVo.setAvatar_url(headerImgUrl);
        authorVo.setLoginname(authorName);
        blogDetailVo.setAuthor(authorVo);
        //处理自己的属性
        List<RepliesVo> replies = new ArrayList<>();
        blogDetailVo.setReplies(replies);
        blogDetailVo.set_collect(true);

        ArticleService.addClick(id);

        return blogDetailVo;
    }


    public List<Blog> test() {
        return null;
    }

    @Override
    public String getTableName() {
        return Blog.tableName;
    }
}
