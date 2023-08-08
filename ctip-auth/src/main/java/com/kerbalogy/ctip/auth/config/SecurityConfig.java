package com.kerbalogy.ctip.auth.config;

import com.kerbalogy.ctip.auth.filter.JwtLoginFilter;
import com.kerbalogy.ctip.auth.filter.JwtVerifyFilter;
import com.kerbalogy.ctip.auth.handler.CustomAuthenticationEntryPoint;
import com.kerbalogy.ctip.auth.handler.UserAuthAccessDeniedHandler;
import com.kerbalogy.ctip.auth.service.impl.UserDetailsServiceImpl;
import com.kerbalogy.ctip.auth.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    JwtLoginFilter jwtLoginFilter;

    @Autowired
    JwtVerifyFilter jwtVerifyFilter;

    @Autowired
    UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;

    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


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

    @Bean
    public BCryptPasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(
                        auth -> {
                            try {
                                auth// 放通静态
                                        .requestMatchers(loadExcludePath()).permitAll()
                                        // 放通注册
                                        .requestMatchers(HttpMethod.POST, "/user/add").permitAll()
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/logger/**").hasAnyRole("ADMIN", "LOGGER")
                                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "LOGGER", "USER")
                                        .requestMatchers("/auth/user/login").anonymous()
                                        //其余请求都需要认证后访问
                                        .anyRequest()
                                        .authenticated()
                                        .and()
//                                        .addFilter(jwtLoginFilter)
//                                        .addFilter(jwtVerifyFilter)
                                        // 已认证但是权限不够
                                        .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                                        .and()
                                        // 未认证（未登陆）
                                        .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                                        .and()
                                        // 禁用 session
                                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
//        httpSecurity.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
