### 文章列表
#sql("list")
	SELECT
	b.id,
	b.title,
	LEFT ( b.content, 50 ) AS content,
	b.createAt,
	b.updateAt,
	b.clickCount,
	b.category,
	b.tag_id AS tagId,
	tag.`name` AS tagName
FROM
	blog b,
	blog_tag tag
WHERE
	b.tag_id = tag.id
	AND b.isDelete = 0
	AND b.category != 'about'
	#if(tagId != null)
    and b.tag_id = #para(tagId)
	#end
ORDER BY
	b.clickCount DESC,
	b.updateAt DESC,
	b.createAt DESC
#end