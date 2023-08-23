package com.kerbalogy.ctip.auth.security.filter;

import com.kerbalogy.ctip.auth.constant.HeaderConstant;
import com.kerbalogy.ctip.auth.constant.RedisKey;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerbalogy.ctip.auth.util.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-16
 * @description
 **/
@Slf4j
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final RedisCache redisCache;

    private final JwtService jwtService;

    public RefreshTokenFilter(RedisCache redisCache, JwtService jwtService) {
        this.redisCache = redisCache;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Enter: RefreshTokenFilter. 验证access token，过期则重新申请并返回");
        String token = request.getHeader(HeaderConstant.HEADER_TOKEN);
        if (token == null || token.isBlank()) {
            // 没有携带Token，直接不通过
            log.info("未携带token");
            filterChain.doFilter(request, response);
            return;
        }

        if (! jwtService.expired(token)) {
            // 没过期，放行
            filterChain.doFilter(request, response);
            return;
        }

        // 直接返回前端
        log.info("当前Access Token过期，重定向申请新的Access Token");
        // 检查refresh token还有没有效果
        String username = jwtService.parseJwtSubject(token, String.class);
        String refreshToken = redisCache.getCacheObject(RedisKey.REFRESH.concat(username), String.class);

        if (refreshToken == null || jwtService.expired(refreshToken)) {
            log.info("refresh token也失效，请重新申请");
            // TODO: spring security异常处理
            throw new AccessDeniedException("已过期，请重新登录。");
        }

        // 没过期，重新申请

        // 重定向
        response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        response.setHeader("Location", "/api/auth/token/refresh?username=" + username);
    }


}
