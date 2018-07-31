package com.choxsu.web.front.blog;

import com.choxsu.common.auto.Inject;
import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.web.front.index.IndexService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.Objects;


/**
 * @author chox su
 * @date 2018/01/06 12:41
 */
public class BlogService {

    @Inject
    IndexService indexService;


    public Record findBlog(Integer id) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id,title,content,createAt,updateAt,clickCount,category,tag_id as tagId,category_id as categoryId ");
        sb.append("FROM blog WHERE id = ? ");
        String sql = sb.toString();
        Record blog = Db.findFirst(sql, id);
        if (blog == null){
            return null;
        }
        Integer tagId = blog.getInt("tagId");
        if (tagId != null && tagId > 0) {
            indexService.doTagNameSet(blog, tagId);
        }
        String category = blog.getStr("category");
        Integer categoryId = blog.getInt("categoryId");
        if (Objects.equals(category, CategoryEnum.CODE.getName()) && Objects.nonNull(categoryId)) {
            indexService.doCodeCategoryNameSet(blog, categoryId);
        }
        BlogService.addClick(id);
        return blog;
    }

    public static void addClick(int id){
        Db.update("UPDATE blog b set b.clickCount = b.clickCount + 1 WHERE id = ?", id);
    }

    /**
     * 获取 不同类型的博客
     *
     * @param pageNumber   当前页
     * @param pageSize     每页条数
     * @param categoryName 分类名称
     * @return
     */
    public Page<Record> findBlogListByCategory(Integer pageNumber, Integer pageSize, String categoryName) {
        assert categoryName != null;

        String select = "SELECT id,title,content,createAt,updateAt,clickCount,category,tag_id as tagId,category_id as categoryId ";

        StringBuilder sb = new StringBuilder();
        sb.append("FROM blog where isDelete = ? and category = ? ORDER BY clickCount DESC,updateAt DESC,createAt DESC");

        String from = sb.toString();
        Page<Record> page = Db.paginate(pageNumber, pageSize, select, from, 0, categoryName);
        indexService.filedHandle(page);
        return page;
    }
}
