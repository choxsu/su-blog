package com.choxsu.common.redis;

import com.jfinal.kit.StrKit;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author choxsu
 * @date 2019/01/07
 */
public class RedisCluster {

    // 主集群缓存
    static JedisCluster mainCache = null;

    // 集群缓存集合
    private static final ConcurrentHashMap<String, JedisCluster> cacheMap = new ConcurrentHashMap<>();


    /**
     * 插入新集群缓存
     *
     * @param cacheName 集群缓存名称
     * @param cache     集群缓存
     */
    public static void addCache(String cacheName, JedisCluster cache) {
        if (cache == null) {
            throw new IllegalArgumentException("cache can not be null");
        }
        if (cacheMap.containsKey(cacheName)) {
            throw new IllegalArgumentException("The cache name already exists");
        }
        cacheMap.put(cacheName, cache);
        if (mainCache == null) {
            mainCache = cache;
        }
    }

    /**
     * 删除集群缓存
     *
     * @param cacheName 集群缓存名称
     * @return JedisCluster
     */
    public static JedisCluster removeCache(String cacheName) {
        return cacheMap.remove(cacheName);
    }

    /**
     * 提供一个设置设置主集群缓存 mainCache 的机会，否则第一个被初始化的 Cache 将成为 mainCache
     */
    public static void setMainCache(String cacheName) {
        if (StrKit.isBlank(cacheName))
            throw new IllegalArgumentException("cacheName can not be blank");
        cacheName = cacheName.trim();
        JedisCluster cache = cacheMap.get(cacheName);
        if (cache == null)
            throw new IllegalArgumentException("the cache not exists: " + cacheName);
        RedisCluster.mainCache = cache;
    }

    /**
     * 使用主集群缓存
     *
     * @return JedisCluster
     */
    public static JedisCluster use() {
        return mainCache;
    }

    /**
     * 使用指定名称集群缓存
     *
     * @param cacheName 集群缓存名称
     * @return JedisCluster
     */
    public static JedisCluster use(String cacheName) {
        return cacheMap.get(cacheName);
    }

}
