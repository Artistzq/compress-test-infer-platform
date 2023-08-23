package com.kerbalogy.ctip.auth.security.config;

import com.kerbalogy.ctip.auth.security.filter.JwtAuthenticationFilter;
import com.kerbalogy.ctip.auth.security.filter.RefreshTokenFilter;
import com.kerbalogy.ctip.auth.security.handler.*;
import com.kerbalogy.ctip.auth.security.service.JwtService;
import com.kerbalogy.ctip.auth.security.service.UserDetailsServiceImpl;
import com.kerbalogy.ctip.auth.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@EnableMethodSecurity
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RedisCache redisCache;

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
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(
                        auth -> {
                            try {
                                auth// 放通静态
                                        .requestMatchers(loadExcludePath()).permitAll()
                                        .requestMatchers(publicEndpoints()).permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        // 自定义过滤器
                                        .addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService),
                                                UsernamePasswordAuthenticationFilter.class)
                                        .addFilterBefore(new RefreshTokenFilter(redisCache, jwtService),
                                                JwtAuthenticationFilter.class)
                                        // 异常处理
                                        .exceptionHandling()
                                        .authenticationEntryPoint(new UserAuthAuthenticationEntryPoint())
                                        .accessDeniedHandler(new UserAuthAccessDeniedHandler())
                                        .and()
                                        .formLogin()
                                        // 配置自定义的登录页面
                                        .loginPage("/api/auth/login")
                                        .permitAll()
                                        // 配置自定义的登录成功处理程序
                                        .successHandler(new UserAuthSuccessHandler(jwtService, redisCache))
                                        // 配置自定义的登录失败处理程序
                                        .failureHandler(new UserAuthFailureHandler())
                                        .and()
                                        .logout()
                                        // 配置自定义的登出URL
                                        .logoutUrl("/api/auth/logout")
                                        // 配置自定义的登出成功处理程序
                                        .logoutSuccessHandler(new RestLogoutSuccessHandler(redisCache, jwtService))
                                        .and()
                                        .sessionManagement()
                                        // 不使用会话
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        /**
         * permitAll对权限校验的fillter没有影响，Security先认证后授权，permitAll是授权部分
         * 这里配置的意思就是，认证部分也不管了。
         */
        return (web -> web.ignoring()
                .requestMatchers("/api/auth/token/refresh")
                .requestMatchers(loadExcludePath())
                .requestMatchers(publicEndpoints())
        );
    }
}
