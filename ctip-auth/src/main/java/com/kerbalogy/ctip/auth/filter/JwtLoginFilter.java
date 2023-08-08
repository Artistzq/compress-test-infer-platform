package com.kerbalogy.ctip.auth.filter;

import com.kerbalogy.ctip.auth.entity.Role;
import com.kerbalogy.ctip.auth.entity.SecurityUser;
import com.kerbalogy.ctip.auth.util.JwtUtil;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description 颁发Token
 **/
@Deprecated
//@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtUtil jwtUtil;
//
//    /**
//     * 验证通过，则返回Authentication，否则返回错误，账号密码不正确
//     * @param request
//     * @param response
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        SecurityUser user = null;
//        try {
//            // 返回Authentication
//            user = JacksonUtil.from(request.getInputStream(), SecurityUser.class);
//            // 注入的这个manager利用了UserDetailsImpl去从数据库拿信息，然后返回到这里
//            // 如果验证成功，则包装到authenticate中去了
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            user.getUsername(),
//                            user.getPassword()
//                    )
//            );
//        } catch (Exception e) {
//            System.out.println("manager从Details拿到的信息错误");
//            try {
//                response.setContentType("application/json;charset=utf-8");
//                response.setStatus(HttpServletResponse.SC_OK);
//                PrintWriter out = response.getWriter();
//                out.write(JacksonUtil.to(JsonResultVO.fail(HttpServletResponse.SC_UNAUTHORIZED, "账号或密码错误!")));
//                out.flush();
//                out.close();
//                e.printStackTrace();
//            }catch (Exception exception){
//                exception.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 登陆成功，颁发token
//     * @param request
//     * @param response
//     * @param chain
//     * @param authResult
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        SecurityUser user = new SecurityUser();
//        user.setUsername(authResult.getName());
//        user.setRoles((List<Role>) authResult.getAuthorities());
//
//        String token = jwtUtil.createJWT(String.valueOf(user.getId()));
//
//
//
//
//    }
}
