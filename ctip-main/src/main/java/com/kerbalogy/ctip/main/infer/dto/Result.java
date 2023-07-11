package com.kerbalogy.ctip.main.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @description
 * @date 2023-06-26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result <T> {

    private T data;

    private String msg;

    private Integer code;

}
