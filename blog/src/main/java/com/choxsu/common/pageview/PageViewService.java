package com.choxsu.common.pageview;

import com.choxsu.common.entity.Visitor;
import com.choxsu.utils.kit.IpKit;
import com.choxsu.front.article.ArticleService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.ehcache.CacheKit;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author choxsu
 * @date 2018/12/20
 */
public class PageViewService {

    public static final PageViewService me = new PageViewService();

    private static final String cacheName = "articlePageView";
    private static final String cacheClickName = "articleClickPageView";

    public void processPageView(Controller c) {
        HttpServletRequest request = c.getRequest();
        String url = request.getRequestURL().toString();
        String ip = IpKit.getRealIp(request);
        String method = request.getMethod();
        String userAgent = request.getHeader("user-agent");
        if (ip == null) {
            ip = "127.0.0.1";
        }
        //存入缓存，后续将数据同步到数据库
        Visitor visitor = CacheKit.get(cacheName, url);
        if (visitor != null) {
            return;
        }
        visitor = new Visitor();
        visitor.setIp(ip);
        AddressVo address = IpKit.getAddress(ip);
        visitor.setAddress(address.getAddress());
        visitor.setAddressJson(address.getAddressJson());
        visitor.setUrl(url);
        visitor.setClient(userAgent);
        visitor.setMethod(method);
        visitor.setRequestTime(new Date());
        CacheKit.put(cacheName, url, visitor);
    }

    void updateToDataBase() {
        this.updatePvDataBase();
        this.updateArticleClickToDataBase();

    }

    /**
     * 将详情页面浏览次数先缓存
     *
     * @param id 文章id
     * @param ip 请求ip
     */
    public void processArticleToCache(Integer id, String ip) {
        if (id == null) {
            throw new IllegalArgumentException("id 值不能为 null.");
        }
        if (ip == null) {
            ip = "127.0.0.1";
        }
        //ip 当成 key
        String pageViewKey = ip;
        Integer idInCache = CacheKit.get("pageViewIp", pageViewKey);

        // 为了避免恶意刷榜，id 在 cache中不存在，或者存在但不等于 id，才去做计数，否则直接跳过
        if (!id.equals(idInCache)) {
            Integer visitCount = CacheKit.get(cacheClickName, id);
            visitCount = (visitCount != null ? visitCount + 1 : 1);
            CacheKit.put(cacheClickName, id, visitCount);
            // 将当前访问者的 actionKey + ip ---> id 放入缓存
            CacheKit.put("pageViewIp", pageViewKey, id);
        }

    }

    /**
     * 更新页面浏览记录到数据库
     */
    private void updatePvDataBase() {
        List urls = CacheKit.getKeys(cacheName);
        List<Visitor> list = new ArrayList<>();
        for (Object urlKey : urls) {
            Visitor visitor = CacheKit.get(cacheName, urlKey.toString());
            if (visitor == null) {
                continue;
            }
            String key = visitor.getUrl();
            list.add(visitor);
            // 获取以后立即清除，因为获取后的值将累加到数据表中。或许放在 for 循环的最后一行为好
            CacheKit.remove(cacheName, key);
        }
        Db.batchSave(list, 100);
        //LogKit.info("请求记录保存成功记录数：" + ints.length);

    }
    /**
     * 更新帖子详情查看次数到数据库，数据本地1分钟更新一次，正式服10分钟更新一次
     */
    private void updateArticleClickToDataBase() {
        List ids = CacheKit.getKeys(cacheClickName);
        for (Object id : ids) {
            Integer visitCount = CacheKit.get(cacheClickName, id);
            if (visitCount == null) {
                continue;
            }
            ArticleService.addClick(id, visitCount);
            // 获取以后立即清除，因为获取后的值将累加到数据表中
            CacheKit.remove(cacheClickName, id);
        }

    }
}
