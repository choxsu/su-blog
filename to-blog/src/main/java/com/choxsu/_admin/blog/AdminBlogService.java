package com.choxsu._admin.blog;

import com.choxsu.common.base.BaseService;
import com.choxsu.common.entity.Blog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogService extends BaseService<Blog> {

    public static final AdminBlogService me = new AdminBlogService();

    public Page<Blog> paginate(Integer p) {

        String sql = "from blog where isDelete = 0";
        Page<Blog> paginate = DAO.paginate(p, 10, "select * ", sql);
        for (Blog blog : paginate.getList()) {
            blog.put("tagName", Db.queryStr("select name from blog_tag where id = ?", blog.getTagId()));
        }
        return paginate;
    }
}
