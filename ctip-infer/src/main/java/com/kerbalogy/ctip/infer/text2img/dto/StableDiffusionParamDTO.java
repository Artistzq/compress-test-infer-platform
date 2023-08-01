package com.kerbalogy.ctip.infer.text2img.dto;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StableDiffusionParamDTO {

    private String prompt;

    private String negativePrompt;

}
