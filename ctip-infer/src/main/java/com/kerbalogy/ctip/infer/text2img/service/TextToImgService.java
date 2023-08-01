package com.kerbalogy.ctip.infer.text2img.service;

import com.kerbalogy.ctip.infer.text2img.dto.StableDiffusionParamDTO;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
public interface TextToImgService {

    Boolean requestTextToImgStableDiffusion(String prompt);

    Boolean requestTextToImgStableDiffusion(StableDiffusionParamDTO stableDiffusionParamDTO);

}
