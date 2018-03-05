#include("")

### blog

#namespace("blog")

  #include("blog.sql")

  #sql("searchCount")
    SELECT
      COUNT(0) as t
    FROM
      blog
    WHERE
      isDelete = 0
      AND title like concat('%',#para(keyword), '%')
  #end

  #sql("searchList")
    SELECT
      id,
      title,
      content,
      createAt,
      updateAt,
      clickCount,
      category,
      tag_id as tagId,
      category_id as categoryId
    FROM
      blog
    WHERE
      isDelete = 0
      AND title like concat('%',#para(keyword), '%')
      ORDER BY clickCount DESC,updateAt DESC,createAt DESC
      limit #(start),#(pageSize)
  #end
#end