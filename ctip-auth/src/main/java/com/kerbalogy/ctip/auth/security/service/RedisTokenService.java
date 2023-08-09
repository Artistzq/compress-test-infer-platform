package com.kerbalogy.ctip.auth.security.service;

import com.kerbalogy.ctip.auth.entity.OldSecurityUser;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
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
    private JwtUtil jwtUtil;

    public String createToken(Authentication authentication) {
        String token = null;
        try {
            token = jwtUtil.createJWT(SecurityUserDetails.getCurrentUser().getUser().getId().toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("当前SecurityUserDetails不含有User", e);
        }
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
        SecurityUserDetails userDetails = (SecurityUserDetails) redisCache.getCacheObject(AUTHORITIES_KEY.concat(token));
        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        }
        return null;
    }

    public Boolean validateToken(String token) {
        SecurityUserDetails userDetails = (SecurityUserDetails) redisCache.getCacheObject(AUTHORITIES_KEY.concat(token));
        return userDetails != null;
    }
}
