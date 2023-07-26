package com.kerbalogy.ctip.cache.base;

import com.kerbalogy.ctip.cache.CacheRedisCaffeineProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/26 22:22
 * @description
 */
@Slf4j
public class RedisCaffeineCacheManager implements CacheManager {

    private static ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    private RedisTemplate<Object, Object> redisTemplate;

    private boolean dynamic = true;

    private Set<String> cacheNames;
    {
        cacheNames = new HashSet<>();
        cacheNames.add(CacheNames.CACHE_15MIN);
        cacheNames.add(CacheNames.CACHE_30MIN);
        cacheNames.add(CacheNames.CACHE_60MIN);
        cacheNames.add(CacheNames.CACHE_180MIN);
    }

    public RedisCaffeineCacheManager(CacheRedisCaffeineProperties cacheRedisCaffeineProperties,
                                     RedisTemplate<Object, Object> redisTemplate) {
        super();
        this.cacheRedisCaffeineProperties = cacheRedisCaffeineProperties;
        this.redisTemplate = redisTemplate;
        this.dynamic = this.cacheRedisCaffeineProperties.isDynamic();
    }

    public RedisCaffeineCacheManager() {
    }

    public void clearLocal(String cacheName, Object key) {
        if (cacheName == null) {
            log.info("清除所有本地缓存");
            cacheMap = new ConcurrentHashMap<>();
            return;
        }

        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }

        RedisCaffeineCache redisCaffeineCache = (RedisCaffeineCache) cache;
        redisCaffeineCache.clearLocal(key);
    }

    @Override
    public Cache getCache(String name) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }
}
