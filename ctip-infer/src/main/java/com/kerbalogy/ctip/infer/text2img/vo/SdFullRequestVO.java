package com.kerbalogy.ctip.infer.text2img.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SdFullRequestVO {

    private String prompt;

    private String negativePrompt;

}
