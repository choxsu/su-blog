SELECT
	st.id as store_id, -- 店铺id
	st.name as store_name, -- 店铺名称
	st.uid, -- uid
	st.telephone, -- 电话
	st.street_id,-- 街道id
	st.detail, -- 详情
	st.address, -- 地址
	st.created as store_created, -- 创建时间
	st.updated as store_updated, -- 修改时间
	sd.id as detail_id,-- 详情id
	sd.type as type_name, -- 类型
	sd.tag, -- 标签
	sd.overall_rating,
	sd.navi_location_lng,
	sd.navi_location_lat,
	sd.detail_url,
	sd.comment_num,
	sd.created as detail_created,
	sd.updated as detail_updated,
	sl.id as location_id,
	sl.lng, 
	sl.lat  
FROM
	store st,
	store_detail sd,
	store_location sl 
WHERE
	st.id = sd.store_id 
	AND st.id = sl.store_id
	AND st.updated > date_add(:sql_last_value, interval 8 hour)