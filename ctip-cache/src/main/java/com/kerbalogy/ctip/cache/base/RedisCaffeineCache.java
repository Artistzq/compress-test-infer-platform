package com.kerbalogy.ctip.cache.base;

import com.github.benmanes.caffeine.cache.Cache;
import com.kerbalogy.ctip.cache.CacheRedisCaffeineProperties;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/26 20:48
 * @description 二级缓存实现类
 */
@Slf4j
public class RedisCaffeineCache extends AbstractValueAdaptingCache {

    private String name;

    private RedisTemplate<Object, Object> redisTemplate;

    @Getter
    private Cache<Object, Object> caffeineCache;

    private String cachePrefix;

    private long defaultExpiration = 3600;

    private final Map<String, Long> defaultExpires = new HashMap<>();

    private String topic;

    private Map<String, ReentrantLock> keyLockMap = new ConcurrentHashMap<>();

    {
        defaultExpires.put(CacheNames.CACHE_15MIN, TimeUnit.MINUTES.toSeconds(15));
        defaultExpires.put(CacheNames.CACHE_30MIN, TimeUnit.MINUTES.toSeconds(30));
        defaultExpires.put(CacheNames.CACHE_60MIN, TimeUnit.MINUTES.toSeconds(60));
        defaultExpires.put(CacheNames.CACHE_180MIN, TimeUnit.MINUTES.toSeconds(180));
    }

    protected RedisCaffeineCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    public RedisCaffeineCache(String name, RedisTemplate<Object, Object> redisTemplate,
                              Cache<Object, Object> caffeineCache,
                              CacheRedisCaffeineProperties cacheRedisCaffeineProperties) {
        super(cacheRedisCaffeineProperties.isCacheNullValues());
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.caffeineCache = caffeineCache;
        this.cachePrefix = cacheRedisCaffeineProperties.getCachePrefix();
        this.defaultExpiration = cacheRedisCaffeineProperties.getRedis().getDefaultExpiration();
        this.topic = cacheRedisCaffeineProperties.getRedis().getTopic();
        defaultExpires.putAll(cacheRedisCaffeineProperties.getRedis().getExpires());
    }

    @Override
    protected Object lookup(Object key) {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @NonNull
    public Object getNativeCache() {
        return this;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }
        // redis和caffeine中均不存在

        ReentrantLock lock = keyLockMap.get(key.toString());
        if (lock == null) {
            log.debug("为该key创建锁：{}", key);
            keyLockMap.putIfAbsent(key.toString(), new ReentrantLock());
            lock = keyLockMap.get(key.toString());
        }

        try {
            // TODO：上锁再查？
            lock.lock();
            value = lookup(key);
            if (value != null) {
                return (T) value;
            }
            // 查数据源
            value = valueLoader.call();
            Object storeValue = toStoreValue(value);
            // 查到了保存到缓存
            put(key, storeValue);
            return (T) value;

        } catch (Exception e) {
            throw new ValueRetrievalException(key, valueLoader, e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(Object key, Object value) {
        if (! super.isAllowNullValues() && value == null) {
            // 不允许为空值的缓存，则不放入元素
            this.evict(key);
            return;
        }
        long expire = getExpire();
        log.debug("put: {}, expire: {}", getKey(key), expire);
        redisTemplate.opsForValue().set(getKey(key), toStoreValue(value), expire, TimeUnit.SECONDS);

        //缓存变更时通知其他节点清理本地缓存
        pushDeleteLocalMessage(new CacheMessage(this.name, key));
    }

    @Override
    public void evict(Object key) {
        // 先清除redis缓存
        redisTemplate.delete(getKey(key));

        // 再清楚caffeine的缓存，避免短时间内如果先清除caffeine后，其他请求从redis加载到caffeine
        pushDeleteLocalMessage(new CacheMessage(this.name, key));

        caffeineCache.invalidate(key);
    }

    @Override
    public void clear() {
        Set<Object> keys = redisTemplate.keys(this.name + ":*");
        for (Object key: keys) {
            redisTemplate.delete(key);
        }

        pushDeleteLocalMessage(new CacheMessage(this.name, null));
        caffeineCache.invalidateAll();
    }

    public void clearLocal(Object key) {
        log.debug("清楚本地缓存，[key = {}]", key);
        if (key == null) {
            caffeineCache.invalidateAll();
        } else {
            caffeineCache.invalidate(key);
        }
    }

    private Object getKey(Object key) {
        String keyStr = this.name + ":" + key.toString();
        return StringUtils.isEmpty(this.cachePrefix) ? keyStr : this.cachePrefix + ":" + keyStr;
    }

    private long getExpire() {
        long expire = defaultExpiration;
        Long cacheNameExpire = defaultExpires.get(this.name);
        return cacheNameExpire == null ? expire : cacheNameExpire;
    }

    private void pushDeleteLocalMessage(CacheMessage cacheMessage) {
        redisTemplate.convertAndSend(topic, cacheMessage);
    }
}
