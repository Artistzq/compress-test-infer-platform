package com.kerbalogy.ctip.auth.filter;

import com.kerbalogy.ctip.auth.entity.SecurityUser;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description 对Token验证和续签
 **/
@Component
public class JwtVerifyFilter extends OncePerRequestFilter {

    @Autowired
    RedisCache redisCache;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization_Token");
        if (token == null) {
            // 不应该在此处抛出异常，抛出异常会重定向至/error，但/error并未放通
            // 故会出现只要是出现了异常，如404等都会被AuthenticationEntryPoint捕获从而返回401
            // throw new AccessDeniedException("未登录");
            chain.doFilter(request, response);
            return;
        }

        // 解析token
        JWTClaimsSet jwtClaimsSet = jwtUtil.parseJWT(token);
        String userId = jwtUtil.parseClaim(jwtClaimsSet, String.class);
        if (redisCache.getCacheObject(token) == null) {
            throw new AccessDeniedException("登录凭证已过期");
        }

        //从redis中获取用户信息
        String redisKey = "userInfo:" + userId;
        SecurityUser securityUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(securityUser)){
            throw new RuntimeException("用户未登录");
        }

        // 怎么续约？先不管续约 TODO：过期续约

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        chain.doFilter(request, response);
    }
}