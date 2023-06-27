package com.kerbalogy.ctip.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-26
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoDTO {

    private Integer type;

    private String name;
}
