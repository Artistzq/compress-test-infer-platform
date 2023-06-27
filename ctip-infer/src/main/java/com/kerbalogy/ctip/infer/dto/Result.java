package com.kerbalogy.ctip.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-26
 **/
@Data
@Builder
@AllArgsConstructor
public class Result <T> {

    private T data;

    private String msg;

    private Integer code;

}
