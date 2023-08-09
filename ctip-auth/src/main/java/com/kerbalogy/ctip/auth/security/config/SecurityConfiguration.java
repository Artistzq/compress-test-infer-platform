package com.kerbalogy.ctip.auth.security.config;

import com.kerbalogy.ctip.auth.security.filter.AuthenticationFilter;
import com.kerbalogy.ctip.auth.security.handler.*;
import com.kerbalogy.ctip.auth.security.service.RedisTokenService;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    RedisTokenService redisTokenService;

    @Autowired
    JwtUtil jwtUtil;

    private String[] loadExcludePath() {
        return new String[]{
                "/static/**",
                "/templates/**",
                "/img/**",
                "/js/**",
                "/css/**",
                "/lib/**"
        };
    }

    private String[] publicEndpoints() {
        return new String[] {
            "/swagger-ui/**",
            "/test/**",
            "/i18n/**",
            "/doc.html",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
        };
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(
                        auth -> {
                            try {
                                auth// 放通静态
                                        .requestMatchers(loadExcludePath()).permitAll()
                                        .requestMatchers(publicEndpoints()).permitAll()
                                        .and()
                                        // 异常处理
                                        .exceptionHandling()
                                        .authenticationEntryPoint(new UserAuthAuthenticationEntryPoint())
                                        .accessDeniedHandler(new UserAuthAccessDeniedHandler())
                                        .and()
//                                        // 启用"记住我"功能
//                                        .rememberMe()
                                        .formLogin()
                                        // 配置自定义的登录页面
                                        .loginPage("/api/auth/login")
                                        .permitAll()
                                        // 配置自定义的登录成功处理程序
                                        .successHandler(new UserAuthSuccessHandler(redisTokenService))
                                        // 配置自定义的登录失败处理程序
                                        .failureHandler(new UserAuthFailureHandler())
                                        .and()
                                        .logout()
                                        // 配置自定义的登出URL
                                        .logoutUrl("/api/auth/logout")
                                        // 配置自定义的登出成功处理程序
                                        .logoutSuccessHandler(new RestLogoutSuccessHandler(redisTokenService))
                                        .and()
                                        .sessionManagement()
                                        // 不使用会话
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        // 在UsernamePasswordAuthenticationFilter之前添加自定义的过滤器
        httpSecurity.addFilterBefore(new AuthenticationFilter(redisTokenService, jwtUtil),
                UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
