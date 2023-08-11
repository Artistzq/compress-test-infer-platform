package com.kerbalogy.ctip.auth.security.filter;

import com.kerbalogy.ctip.auth.constant.HeaderConstant;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("Enter: JwtAuthenticationFilter. 验证token，根据token，构建Authentication对象.");

        String token = request.getHeader(HeaderConstant.HEADER_TOKEN);
        if (token == null) {
            // 没有携带Token，直接不通过
            log.info("未携带token");
            filterChain.doFilter(request, response);
            return;
        }

        // 先实现完全无状态的，只用JWT，不用Redis
        // 验证token
        if (jwtService.expired(token)) {
            log.error("token过期");
            throw new AccessDeniedException("登录凭证已过期");
        }

        String username = jwtService.parseJwtSubject(token, String.class);
        // 通过token验证。token合法，就通过。到这里已经登陆成功了。用户信息通过token里的userId获取的。
        // 这里应该是要把Authentication存入的
        // 当场构建一个Authentication，通过token
        SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(username);
        // 封装 Authentication

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //放行
        filterChain.doFilter(request, response);
        log.info("Quit: JwtAuthenticationFilter.");
    }
}
