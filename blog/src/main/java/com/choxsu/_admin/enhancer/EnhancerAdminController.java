package com.choxsu._admin.enhancer;

import com.choxsu.common.base.BaseController;
import com.jfinal.aop.Inject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import net.sf.ehcache.CacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choxsu
 * @date 2018/12/26
 */
public class EnhancerAdminController extends BaseController {

    @Inject
    EnhancerAdminService enhancerAdminService;

    public void index() {
        List<EnhancerShowVo> enhancerShowVos = enhancerAdminService.getCaches();
        setAttr("caches", enhancerShowVos);
        render("index.html");
    }


    public void keys() {
        String key = getPara("key");
        String cacheName = getPara("cacheName");
        Object objValue;
        if (StrKit.isBlank(key) || StrKit.isBlank(cacheName)) {
            objValue = "";
        } else {
            objValue = CacheKit.get(cacheName, key);
        }
        setAttr("key", key);
        setAttr("value", objValue);
        render("key.html");
    }

}
