package com.kerblogy.ctip.common.util.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author yaozongqing@eastmoney.com
 * @description Http Servlet 工具类
 * @date 2023-07-06
 **/
public class HttpUtil {

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getHttpServletResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getResponse();
    }

    public static boolean isDebug() {
        HttpServletRequest request = getHttpServletRequest();
        String debug = request.getParameter("debug");
        return "true".equalsIgnoreCase(debug);
    }

}
