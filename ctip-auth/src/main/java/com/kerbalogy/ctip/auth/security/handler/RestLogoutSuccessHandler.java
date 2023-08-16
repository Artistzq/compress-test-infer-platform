package com.kerbalogy.ctip.auth.security.handler;

import com.kerbalogy.ctip.auth.constant.HeaderConstant;
import com.kerbalogy.ctip.auth.constant.RedisKey;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerbalogy.ctip.auth.security.util.ResponseUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Slf4j
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisCache redisCache;

    private final JwtService jwtService;

    public RestLogoutSuccessHandler(RedisCache redisCache, JwtService jwtService) {
        this.redisCache = redisCache;
        this.jwtService = jwtService;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登出处理====>");
        String token = request.getHeader(HeaderConstant.HEADER_TOKEN);
        JsonResultVO<String> jsonResultVO = null;
        if (token == null || token.isBlank()) {
            // 没有携带Token，无法登出
            log.info("未携带token");
            ResponseUtil.setResponse(response, JsonResultVO.fail("登出需要携带Access Token"));
            return;
        }

        if (jwtService.expired(token)) {
            // Token过期，同样无法登出
            log.info("token过期");
            ResponseUtil.setResponse(response, JsonResultVO.fail("Access Token已过期"));
            return;
        }

        String username = jwtService.parseJwtSubject(token, String.class);
        redisCache.deleteObject(RedisKey.REFRESH.concat(username));
        ResponseUtil.setResponse(response, JsonResultVO.success(null, String.format("账户 [username=%s] 已经登出", username)));
    }
}
