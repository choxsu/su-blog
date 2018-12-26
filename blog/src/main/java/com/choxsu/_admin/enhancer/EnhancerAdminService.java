package com.choxsu._admin.enhancer;

import com.jfinal.plugin.ehcache.CacheKit;
import net.sf.ehcache.CacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author choxsu
 * @date 2018/12/26
 */
public class EnhancerAdminService {


    public List<EnhancerShowVo> getCaches() {
        List<EnhancerShowVo> enhancerShowVos = new ArrayList<>();

        CacheManager cacheManager = CacheKit.getCacheManager();
        String[] cacheNames = cacheManager.getCacheNames();
        EnhancerShowVo enhancerShowVo;
        for (String cacheName : cacheNames) {
            enhancerShowVo = new EnhancerShowVo();
            enhancerShowVo.setCacheName(cacheName);
            List keys = CacheKit.getKeys(cacheName);
            enhancerShowVo.setKeys(keys);
            for (Object key : keys) {
                Object o = CacheKit.get(cacheName, key);
                System.out.println(o);
            }
            enhancerShowVos.add(enhancerShowVo);
        }

        return enhancerShowVos;
    }
}
