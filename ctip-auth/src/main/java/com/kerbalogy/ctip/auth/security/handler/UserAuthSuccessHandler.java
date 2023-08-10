package com.kerbalogy.ctip.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kerbalogy.ctip.auth.entity.User;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.security.service.RedisTokenService;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
public class UserAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTokenService redisTokenService;

    public UserAuthSuccessHandler(RedisTokenService redisTokenService) {
        this.redisTokenService = redisTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("成功");
        // 颁发token
        String token = redisTokenService.createToken(authentication);
        User user = ((SecurityUserDetails) authentication.getPrincipal()).getUser();
        LoginDTO loginDTO = LoginDTO.builder()
                .accessToken(token)
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
        // 如果有旧token，就删除旧token


        JsonResultVO<LoginDTO> result = JsonResultVO.success(loginDTO);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().append(JacksonUtil.to(result));
        } catch (IOException e) {
            throw new BadCredentialsException("登录异常：" + e.getMessage());
        }

    }

    @Data
    @Builder
    public static class LoginDTO {
        private String accessToken;
        private String username;
        private String phone;
    }
}
