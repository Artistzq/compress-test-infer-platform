package com.kerbalogy.ctip.auth.util;

import com.kerbalogy.ctip.auth.entity.Role;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisCacheTest {

    @Autowired
    RedisCache redisCache;

    @Test
    void setCacheObject() {

        Role role = new Role();
        role.setId(10L);
        role.setCreatedBy("y");
        redisCache.setCacheObject("user", JacksonUtil.to(role), 10L, TimeUnit.SECONDS);
        String ret = redisCache.getCacheObject("user");

        System.out.println(ret.getClass());

        System.out.println(JacksonUtil.from(ret, Role.class));

        System.out.println(ret);

    }
}