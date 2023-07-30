package com.kerbalogy.ctip.cache;

import com.kerbalogy.ctip.cache.base.CacheMessageListener;
import com.kerbalogy.ctip.cache.base.RedisCaffeineCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/26 21:09
 * @description
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
@Slf4j
public class CacheRedisCaffeineAutoConfiguration {

    @Autowired
    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

    @Bean("L2CacheManager")
    @ConditionalOnBean(RedisTemplate.class)
    public RedisCaffeineCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
        log.info("===========================================");
        log.info("=                                         =");
        log.info("=          Two Level Cache Start          =");
        log.info("=                                         =");
        log.info("===========================================");
        return new RedisCaffeineCacheManager(cacheRedisCaffeineProperties, redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisTemplate<Object, Object> redisTemplate, RedisCaffeineCacheManager redisCaffeineCacheManager) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate, redisCaffeineCacheManager);
        redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(
                cacheRedisCaffeineProperties.getRedis().getTopic()
        ));
        return redisMessageListenerContainer;
    }

}
