package com.kerblogy.ctip.common.models.dto;

/**
 * @author yaozongqing@outlook.com
 * @description 内部服务的响应类
 * @date 2023-07-05
 **/
public class ServiceResponse <T> {

    private Integer code;

    private String msg;

    private String source;

    private T data;

}
