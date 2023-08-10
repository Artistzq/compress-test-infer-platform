package com.kerbalogy.ctip.auth.filter;

import com.kerbalogy.ctip.auth.entity.OldSecurityUser;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerbalogy.ctip.auth.util.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization_Token");
        if (token == null) {
            // 不应该在此处抛出异常，抛出异常会重定向至/error，但/error并未放通
            // 故会出现只要是出现了异常，如404等都会被AuthenticationEntryPoint捕获从而返回401
            // throw new AccessDeniedException("未登录");
            chain.doFilter(request, response);
            return;
        }

        // 解析token
        String userId = jwtService.parseJwtSubject(token, String.class);
        if (redisCache.getCacheObject(token) == null) {
            throw new AccessDeniedException("登录凭证已过期");
        }

        //从redis中获取用户信息
        String redisKey = "userInfo:" + userId;
        OldSecurityUser oldSecurityUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(oldSecurityUser)){
            throw new RuntimeException("用户未登录");
        }

        // 怎么续约？先不管续约 TODO：过期续约

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(oldSecurityUser,null, oldSecurityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        chain.doFilter(request, response);
    }
}
