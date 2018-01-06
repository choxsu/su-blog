package com.choxsu.index;

import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.common.constant.EnCacheEnum;
import com.choxsu.common.safe.JsoupFilter;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Objects;

/**
 * @author choxsu
 */
public class IndexService {

    public static final IndexService me = new IndexService();

    /**
     * 查询所有有效的tags
     *
     * @return
     */
    public List<Record> findBlogTags() {
        return Db.findByCache(EnCacheEnum.TAGS.getName(), EnCacheEnum.TAGS.getKey(), "SELECT id,name FROM blog_tag WHERE status = ?", 0);
    }

    /**
     * 清除TagsCache
     */
    public void clearTagsCache() {
        CacheKit.remove(EnCacheEnum.TAGS.getName(), EnCacheEnum.TAGS.getKey());
    }

    /**
     * 查询所有博客分页
     *
     * @param page
     * @return
     */
    public Page<Record> findBlogs(int page) {
        String select = "SELECT id,title,content,createAt,updateAt,clickCount,category,tag_id as tagId,category_id as categoryId";
        String from = "FROM blog WHERE isDelete = ? ORDER BY clickCount DESC,updateAt DESC,createAt DESC";
        Page<Record> result = Db.paginate(page, 15, select, from, 0);
        filedHandle(result);
        return result;
    }

    /**
     * 博客记录，列表字段处理
     * @param result
     */
    public void filedHandle(Page<Record> result) {
        result.getList().forEach(s -> {
            String category = s.getStr("category");
            if (!Objects.equals(category, CategoryEnum.ABOUT.getName())){
                s.set("content", JsoupFilter.getText(s.get("content"), 320));
            }
            Integer tagId = s.getInt("tagId");
            if (tagId != null && tagId > 0) {
                doTagNameSet(s, tagId);
            }

            Integer categoryId = s.getInt("categoryId");
            if (Objects.equals(category, CategoryEnum.CODE.getName()) && Objects.nonNull(categoryId)) {
                doCodeCategoryNameSet(s, categoryId);
            }
        });
    }

    /**
     * 封装tag名称
     *
     * @param s
     * @param tagId
     */
    public void doTagNameSet(Record s, Integer tagId) {
        assert s != null;
        assert tagId != null && tagId > 0;
        Record record = Db.findById("blog_tag", tagId);
        String name = record.getStr("name");
        s.set("tagName", name);
    }

    /**
     * 封装categoryId名称，当category值为note时候
     *
     * @param s
     * @param categoryId
     */
    public void doCodeCategoryNameSet(Record s, Integer categoryId) {
        assert s != null;
        assert categoryId != null && categoryId > 0;
        Record record = Db.findById("blog_category", categoryId);
        String name = record.getStr("name");
        s.set("categoryName", name);
    }
}
