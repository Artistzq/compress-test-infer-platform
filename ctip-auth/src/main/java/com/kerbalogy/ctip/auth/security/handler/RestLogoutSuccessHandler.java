package com.kerbalogy.ctip.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerbalogy.ctip.auth.constant.HeaderConstant;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.security.service.RedisTokenService;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerbalogy.ctip.auth.util.RedisCache;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {

    private final RedisTokenService redisTokenService;

    public RestLogoutSuccessHandler(RedisTokenService redisTokenService) {
        this.redisTokenService = redisTokenService;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = request.getHeader(HeaderConstant.HEADER_TOKEN);
        redisTokenService.removeToken(token);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().append(JacksonUtil.to(JsonResultVO.success()));
    }
}
