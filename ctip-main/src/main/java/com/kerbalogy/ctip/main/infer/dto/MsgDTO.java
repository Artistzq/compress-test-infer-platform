package com.kerbalogy.ctip.main.infer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
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
