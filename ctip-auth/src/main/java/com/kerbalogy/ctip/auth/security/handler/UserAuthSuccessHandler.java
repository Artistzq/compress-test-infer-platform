package com.kerbalogy.ctip.auth.security.handler;

import com.kerbalogy.ctip.auth.constant.RedisKey;
import com.kerbalogy.ctip.auth.constant.TokenConstant;
import com.kerbalogy.ctip.auth.entity.User;
import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerbalogy.ctip.auth.util.RedisCache;
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
import java.util.concurrent.TimeUnit;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Slf4j
public class UserAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Data
    @Builder
    public static class LoginDTO {
        private String accessToken;
        private String username;
        private String phone;
    }

    private final JwtService jwtService;

    private final RedisCache redisCache;

    public UserAuthSuccessHandler(JwtService jwtService, RedisCache redisCache) {
        this.jwtService = jwtService;
        this.redisCache = redisCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Enter: Success Handler. 认证成功");

        // 此处认证成功了
        SecurityUserDetails userDetails = SecurityUserDetails.getCurrentUser();
        if (userDetails == null) {
            throw new RuntimeException("用户认证成功后，SpringSecurityContext中却没有用户信息。");
        }

        // TODO：如果登录过了，就不用登录了，也不返回新的accessToken
        if (redisCache.getCacheObject(RedisKey.REFRESH.concat(userDetails.getUsername()), String.class) != null) {
            log.info("已登录，无需再登录，不返回token");
            setResponse(response, JsonResultVO.success("已登录，无需重复登录。"));
            return;
        }

        // 生成一个refresh token和一个access token，返回access token, refresh token落库
        String token = jwtService.createJWT(userDetails.getUsername(), TokenConstant.ACCESS_TIME);
        User user = userDetails.getUser();
        LoginDTO loginDTO = LoginDTO.builder()
                .accessToken(token)
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
        JsonResultVO<LoginDTO> result = JsonResultVO.success(loginDTO);

        // refresh token落库
        String refreshToken = jwtService.createJWT(userDetails.getUsername(), TokenConstant.REFRESH_TIME);
        redisCache.setCacheObject(RedisKey.REFRESH.concat(userDetails.getUsername()), refreshToken,
                TokenConstant.REFRESH_TIME, TimeUnit.MILLISECONDS);
        setResponse(response, result);
    }

    private <T> void setResponse(HttpServletResponse response, T result) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().append(JacksonUtil.to(result));
        } catch (IOException e) {
            throw new BadCredentialsException("登录信息IO异常：" + e.getMessage());
        }
    }

}
