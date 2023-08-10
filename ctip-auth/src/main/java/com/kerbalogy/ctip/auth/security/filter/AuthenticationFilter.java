package com.kerbalogy.ctip.auth.security.filter;

import com.kerbalogy.ctip.auth.constant.HeaderConstant;
import com.kerbalogy.ctip.auth.security.service.RedisTokenService;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final RedisTokenService redisTokenService;

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RedisTokenService redisTokenService, JwtUtil jwtUtil) {
        this.redisTokenService = redisTokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Enter: AuthenticationFilter");

        String token = request.getHeader(HeaderConstant.HEADER_TOKEN);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * TODO：
         * token -> userId
         * userId -> User缓存信息 -> Authentication
         * 这样可以提高缓存复用率
         *
         * 当前：token -> Authentication
         */

        // 解析token
//        JWTClaimsSet jwtClaimsSet = jwtUtil.parseJWT(token);
//        String userId = jwtUtil.parseClaim(jwtClaimsSet, String.class);
        if (! redisTokenService.validateToken(token)) {
            throw new AccessDeniedException("登录凭证已过期");
        }

        Authentication authentication = redisTokenService.getAuthentication(token);
        // 续约
        redisTokenService.refreshExpiration(token);
        //存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());

        //放行
        filterChain.doFilter(request, response);

        log.info("Quit: AuthenticationFilter");
    }
}
