package com.kerbalogy.ctip.cache.base;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.kerbalogy.ctip.cache.CacheRedisCaffeineProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

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

    public void clearAllCache() {
        redisTemplate.convertAndSend(cacheRedisCaffeineProperties.getRedis().getTopic(), new CacheMessage(null, null));
    }

    public static Map<String, CacheStats> getCacheStats() {
        if (CollectionUtils.isEmpty(cacheMap)) {
            return null;
        }

        Map<String, CacheStats> result = new LinkedHashMap<>();
        for (Cache cache: cacheMap.values()) {
            RedisCaffeineCache redisCaffeineCache = (RedisCaffeineCache) cache;
            result.put(redisCaffeineCache.getName(), redisCaffeineCache.getCaffeineCache().stats());
        }
        return result;
    }

    private com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache(String name) {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        CacheRedisCaffeineProperties.CacheDefault cacheConfig = switch (name) {
            case CacheNames.CACHE_15MIN -> cacheRedisCaffeineProperties.getCache15m();
            case CacheNames.CACHE_30MIN -> cacheRedisCaffeineProperties.getCache30m();
            case CacheNames.CACHE_60MIN -> cacheRedisCaffeineProperties.getCache60m();
            case CacheNames.CACHE_180MIN -> cacheRedisCaffeineProperties.getCache180m();
            default -> cacheRedisCaffeineProperties.getCacheDefault();
        };
        long expireAfterAccess = cacheConfig.getExpireAfterAccess();
        long expireAfterWrite = cacheConfig.getExpireAfterWrite();
        int initialCapacity = cacheConfig.getInitialCapacity();
        long maximumSize = cacheConfig.getMaximumSize();
        long refreshAfterWrite = cacheConfig.getRefreshAfterWrite();

        log.debug("本地缓存初始化");
        if (expireAfterAccess > 0) {
            log.debug("设置本地缓存访问后过期时间，{}秒", expireAfterAccess);
            cacheBuilder.expireAfterAccess(expireAfterAccess, TimeUnit.SECONDS);
        }
        if (expireAfterWrite > 0) {
            log.debug("设置本地缓存写入后过期时间，{}秒", expireAfterWrite);
            cacheBuilder.expireAfterWrite(expireAfterWrite, TimeUnit.SECONDS);
        }
        if (initialCapacity > 0) {
            log.debug("设置缓存初始化大小{}", initialCapacity);
            cacheBuilder.initialCapacity(initialCapacity);
        }
        if (maximumSize > 0) {
            log.debug("设置本地缓存最大值{}", maximumSize);
            cacheBuilder.maximumSize(maximumSize);
        }
        if (refreshAfterWrite > 0) {
            cacheBuilder.refreshAfterWrite(refreshAfterWrite, TimeUnit.SECONDS);
        }
        cacheBuilder.recordStats();
        return cacheBuilder.build();
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
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (! dynamic && ! cacheNames.contains(name)) {
            return null;
        }

        cache = new RedisCaffeineCache(name, redisTemplate, caffeineCache(name), cacheRedisCaffeineProperties);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("创建缓存实例：名称=[{}]", name);
        return oldCache == null ? cache : oldCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }
}
