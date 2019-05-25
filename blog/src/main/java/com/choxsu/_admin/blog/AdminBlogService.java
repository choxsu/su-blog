package com.choxsu._admin.blog;

import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.choxsu.common.interceptor.AuthCacheClearInterceptor;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chox su
 * @date 2018/6/12 22:05
 */
public class AdminBlogService {

    private Blog blogDao = new Blog().dao();

    public Page<Blog> list(Integer p, Integer pageSize, Account loginAccount) {

        boolean admin = AuthCacheClearInterceptor.isAdmin(loginAccount);
        String sql = "from blog where isDelete = 0 ";
        List<Object> list = new ArrayList<>();
        if (!admin) {
            sql += "and accountId = ? ";
            list.add(loginAccount.getId());
        }
        Page<Blog> paginate = blogDao.paginate(p, pageSize, "select * ", sql, list.toArray());
        paginate.getList().forEach(this::setTagNameAndAuthor);
        return paginate;
    }

    private void setTagNameAndAuthor(Blog blog) {
        if (blog == null) {
            return;
        }
        String tagName = Db.queryStr("select `name` from blog_tag where id = ?", blog.getTagId());
        blog.setTagName(tagName);
        String nickName = Db.queryStr("SELECT nickName FROM `account` WHERE id = ?", blog.getAccountId());
        blog.setAuthor(nickName);
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
        Blog blog = blogDao.findById(id);
        if (blog == null) {
            return b;
        }
        String oldTitle = blog.getTitle();
        if (oldTitle != null && oldTitle.equals(title)) {
            return false;
        } else {
            return b;
        }

    }

    public Blog findById(Integer id) {
        return blogDao.findById(id);
    }

    public void deleteById(Integer id) {
        Blog blog = findById(id);
        if (blog == null) return;
        blog.setIsDelete(1);
        blog.keep("id", "isDelete");
        blog.update();
    }

    public Ret allowComments(Integer id) {
        return updateAllowComments(id, true);
    }

    public Ret unAllowComments(Integer id) {
        return updateAllowComments(id, false);
    }

    private Ret updateAllowComments(Integer id, boolean isAllowComments) {
        Blog blog = blogDao.findById(id);
        if (blog == null) {
            return Ret.fail("msg", "文章不存在哦");
        }
        blog.setAllowComments(isAllowComments);
        boolean update = blog.update();
        return update ? Ret.ok("msg", "操作成功") : Ret.fail("msg", "操作失败");
    }

    public Ret oneKeyAllowComments() {
        Db.update("update blog set allowComments = 1 where isDelete = 0");
        return Ret.ok("msg", "一键开启评论成功");
    }
}
