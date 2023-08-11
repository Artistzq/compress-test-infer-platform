package com.kerbalogy.ctip.auth.security.service;

import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.util.RedisCache;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Service
public class RedisTokenService {

    private final static String AUTHORITIES_KEY = "auth:";

    private final static TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    private final Integer expiration = 3600;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtService jwtService;

    public String createToken(Authentication authentication) {
        String token = null;
        try {
            token = jwtService.createJWT(SecurityUserDetails.getCurrentUser().getUser().getId().toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("当前SecurityUserDetails不含有User", e);
        }

        System.out.println(JacksonUtil.to(authentication.getPrincipal()));
        System.out.println(authentication.getPrincipal());
        redisCache.setCacheObject(AUTHORITIES_KEY.concat(token), authentication.getPrincipal(), expiration, DEFAULT_TIME_UNIT);
        return token;
    }

    public Boolean refreshExpiration(String token) {
        String key = AUTHORITIES_KEY.concat(token);
        return redisCache.expire(key, expiration, DEFAULT_TIME_UNIT);
    }

    public Boolean removeToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        String key = AUTHORITIES_KEY.concat(token);
        return redisCache.deleteObject(key);
    }

    public Authentication getAuthentication(String token) {
        SecurityUserDetails userDetails = redisCache.getCacheObject(AUTHORITIES_KEY.concat(token), SecurityUserDetails.class);
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }
        return null;
    }

    public Boolean validateToken(String token) {
        SecurityUserDetails userDetails = redisCache.getCacheObject(AUTHORITIES_KEY.concat(token), SecurityUserDetails.class);
        return userDetails != null;
    }
}
