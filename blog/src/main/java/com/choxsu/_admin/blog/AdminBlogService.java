package com.choxsu._admin.blog;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.Blog;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogService extends BaseService<Blog> {


    public Page<Blog> paginate(Integer p) {

        String sql = "from blog where isDelete = 0";
        Page<Blog> paginate = DAO.paginate(p, 10, "select * ", sql);
        for (Blog blog : paginate.getList()) {
            blog.put("tagName", Db.queryStr("select name from blog_tag where id = ?", blog.getTagId()));
        }
        return paginate;
    }

    @Override
    public String getTableName() {
        return Blog.tableName;
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
        Record record = Db.findById(getTableName(), id);
        if (record == null){
            return b;
        }
        String oldTitle = record.getStr("title");
        if (oldTitle != null && oldTitle.equals(title)){
            return false;
        }else {
            return b;
        }

    }
}
