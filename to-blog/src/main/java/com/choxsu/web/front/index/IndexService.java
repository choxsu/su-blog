package com.choxsu.web.front.index;

import com.choxsu.common.constant.CategoryEnum;
import com.choxsu.common.constant.EnCacheEnum;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author choxsu
 */
public class IndexService {

    /**
     * 查询所有有效的tags
     *
     * @return
     */
    public List<Record> findBlogTags() {
        String sql = "SELECT tag.id,tag.name,(SELECT count(0) FROM blog b WHERE tag_id = tag.id and category != ?) as topicNum FROM blog_tag tag WHERE status = ?";

        return Db.findByCache(EnCacheEnum.TAGS.getName(), EnCacheEnum.TAGS.getKey(), sql, CategoryEnum.ABOUT.getName(), 0);

    }

    /**
     * 清除TagsCache
     */
    public void clearTagsCache() {
        CacheKit.remove(EnCacheEnum.TAGS.getName(), EnCacheEnum.TAGS.getKey());
    }

    /**
     * 查询所有博客分页
     * update: 最近两天发布的放在最前面 TODO
     * @param page
     * @return
     */
    public Page<Record> findBlogs(int page) {
        String select = "SELECT id,title,content,createAt,updateAt,clickCount,category,tag_id as tagId,category_id as categoryId";
        String from = "FROM blog WHERE isDelete = ? and category != ? ORDER BY clickCount DESC,updateAt DESC,createAt DESC";
        Page<Record> result = Db.paginate(page, 15, select, from, 0, CategoryEnum.ABOUT.getName());
        filedHandle(result);
        return result;
    }

    /**
     * 博客记录，列表字段处理
     *
     * @param result
     */
    public void filedHandle(Page<Record> result) {
        result.getList().forEach(s -> {
            String category = s.getStr("category");
            if (!Objects.equals(category, CategoryEnum.ABOUT.getName())) {
                s.set("content", delHTMLTag(s.get("content"), 150) + "......");
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

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr, int length) {
        if (StrKit.isBlank(htmlStr)){
            return "";
        }
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        htmlStr = htmlStr.trim();
        if (length > 0 && htmlStr.length() > length){
            htmlStr = htmlStr.substring(0, length);
        }
        return htmlStr; // 返回文本字符串
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
