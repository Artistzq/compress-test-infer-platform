package com.kerbalogy.ctip.auth.security.handler;

import com.kerbalogy.ctip.auth.entity.User;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public UserAuthSuccessHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Enter: Success Handler. 认证成功");

        // 此处认证成功了
        SecurityUserDetails userDetails = SecurityUserDetails.getCurrentUser();
        if (userDetails == null) {
            throw new RuntimeException("用户认证成功后，SpringSecurityContext中却没有用户信息。");
        }

        // 颁发token
        String token = jwtService.createJWT(userDetails.getUsername());
        User user = userDetails.getUser();
        LoginDTO loginDTO = LoginDTO.builder()
                .accessToken(token)
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
        JsonResultVO<LoginDTO> result = JsonResultVO.success(loginDTO);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().append(JacksonUtil.to(result));
        } catch (IOException e) {
            throw new BadCredentialsException("登录信息IO异常：" + e.getMessage());
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
