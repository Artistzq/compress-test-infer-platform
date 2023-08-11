package com.kerbalogy.ctip.auth.constant;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
public class HeaderConstant {

    /**
     * token保存到redis里，为键，里面有ID
     *
     * ID对应一个用户，可以存也可以不存
     *
     * 不存，这个功能由持久化过滤器实现
     */
    public static final String HEADER_TOKEN ="AUTH-TOKEN";

}
