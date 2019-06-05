package com.choxsu._admin.blog;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.interceptor.AuthCacheClearInterceptor;
import com.choxsu.common.redis.RedisKey;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogService extends BaseService<Blog> {


    public Page<Blog> list(Integer p, Integer pageSize, Account loginAccount) {

        boolean admin = AuthCacheClearInterceptor.isAdmin(loginAccount);
        String sql = "from blog where isDelete = 0 ";
        List<Object> list = new ArrayList<>();
        if (!admin) {
            sql += "and accountId = ? ";
            list.add(loginAccount.getId());
        }
        Page<Blog> paginate = DAO.paginate(p, pageSize, "select * ", sql, list.toArray());
        for (Blog blog : paginate.getList()) {
            blog.put("tagName", getTagStr(blog));
        }
        return paginate;
    }

    private String getTagStr(Blog blog) {
        if (blog == null) {
            return null;
        }
        return Db.queryStr("select name from blog_tag where id = ?", blog.getTagId());
    }

    public Ret saveOrUpdateArticle(Blog blog) {
        if (blog == null) {
            return Ret.fail().set("msg", "参数不存在");
        }
        Date date = new Date();
        blog.setUpdateAt(date);
        if (blog.getId() != null) {
            blog.update();
        } else {
            blog.setCreateAt(date);
            blog.save();
        }
        return Ret.ok();
    }

    /**
     * 判断标题是否存在
     *
     * @param id    文章ID
     * @param title 文章标题
     * @return
     */
    public boolean isExistTitle(Integer id, String title) {
        title = title.trim();
        boolean b = Db.queryLong("select count(0) from blog where title = ?", title) > 0;
        if (id == null) {
            return b;
        }
        Record record = Db.findById(tableName, id);
        if (record == null) {
            return b;
        }
        String oldTitle = record.getStr("title");
        if (oldTitle != null && oldTitle.equals(title)) {
            return false;
        } else {
            return b;
        }

    }
}
