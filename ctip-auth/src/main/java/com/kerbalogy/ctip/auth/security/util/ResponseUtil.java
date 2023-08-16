package com.kerbalogy.ctip.auth.security.util;

import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-16
 * @description
 **/
public class ResponseUtil {

    public static  <T> void setResponse(HttpServletResponse response, T data) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().append(JacksonUtil.to(data));
        } catch (IOException e) {
            throw new BadCredentialsException("登录信息IO异常：" + e.getMessage());
        }
    }

}
