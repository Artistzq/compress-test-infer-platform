package com.kerbalogy.ctip.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-27
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgDTO {

    private String talkContent;

    private Integer id;
}
