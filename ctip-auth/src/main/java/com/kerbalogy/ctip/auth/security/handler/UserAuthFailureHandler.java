package com.kerbalogy.ctip.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
public class UserAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        JsonResultVO<Object> resultVO = JsonResultVO.fail(HttpServletResponse.SC_UNAUTHORIZED, "帐号或密码错误。");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().append(JacksonUtil.to(resultVO));

    }
}
