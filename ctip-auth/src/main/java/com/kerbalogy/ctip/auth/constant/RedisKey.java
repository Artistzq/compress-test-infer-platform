package com.kerbalogy.ctip.auth.constant;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-10
 * @description
 **/
public class RedisKey {
    /**
     * auth:${id} -> token
     */
    public final static String AUTHORITIES = "auth:";

    public final static String REFRESH = "refresh:";
}
