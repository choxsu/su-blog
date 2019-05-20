package com.choxsu._front.article;

import com.choxsu.common.entity.Account;
import com.choxsu.common.entity.Blog;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;

/**
 * @author choxsu
 */
public class ArticleService {

    private Blog blogDao = new Blog().dao();
    private Account accountDao = new Account().dao();

    public static void addClick(Object id) {
        Db.update("UPDATE blog set clickCount = clickCount + 1 WHERE id = ?", id);
    }

    public static void addClick(Object id, int count) {
        Db.update("UPDATE blog set clickCount = clickCount + ? WHERE id = ?", count, id);
    }

    /**
     * 查询所有博客分页
     * TODO update: 最近两天发布的放在最前面
     *
     * @param page
     * @return
     */
    public Page<Blog> findArticles(int page, int pageSize, Integer tagId) {
        Kv kv = Kv.create().set("tagId", tagId);
        SqlPara sqlPara = Db.getSqlPara("article.list", kv);
        Page<Blog> paginate = blogDao.paginate(page, pageSize, sqlPara);
        setAvatar(paginate);
        return paginate;
    }

    private void setAvatar(Page<Blog> paginate) {
        for (Blog blog : paginate.getList()) {
            Account account = accountDao.findById(blog.getAccountId());
            if (account == null) {
                continue;
            }
            blog.put("avatar", account.getAvatar());
            blog.put("isThird", account.getIsThird());
        }
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    public Blog findBlog(Integer id) {
        Blog blog = blogDao.findById(id);
        if (blog == null) {
            return null;
        }
        this.doTagNameSet(blog);
        return blog;
    }

    /**
     * 封装tag名称
     *
     * @param blog
     */
    public void doTagNameSet(Blog blog) {
        assert blog != null;
        Record record = Db.findById("blog_tag", blog.get("tag_id"));
        if (record == null) {
            return;
        }
        String name = record.getStr("name");
        blog.setTagName(name);
        Account account = accountDao.findById(blog.getAccountId());
        if (account == null) {
            return;
        }
        blog.setAuthor(account.getNickName());
    }
}
