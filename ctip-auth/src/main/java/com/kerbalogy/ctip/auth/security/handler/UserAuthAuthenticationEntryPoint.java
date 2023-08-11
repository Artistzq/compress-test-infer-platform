package com.kerbalogy.ctip.auth.security.handler;

import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
public class UserAuthAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(JacksonUtil.to(JsonResultVO.fail(HttpServletResponse.SC_UNAUTHORIZED, "请登录！")));
        out.flush();
        out.close();
    }
}
