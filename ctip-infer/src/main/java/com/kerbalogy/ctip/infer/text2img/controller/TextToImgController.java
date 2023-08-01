package com.kerbalogy.ctip.infer.text2img.controller;

import com.kerbalogy.ctip.infer.text2img.service.TextToImgService;
import com.kerbalogy.ctip.infer.text2img.vo.SdFullRequestVO;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@RestController
@RequestMapping("/infer/text2img")
@Schema(description = "文字转图生成器")
public class TextToImgController {

    @Autowired
    TextToImgService textToImgService;

    @PostMapping("/sd")
    public JsonResultVO<Boolean> requestTextToImg(String text) {
        return JsonResultVO.success(textToImgService.requestTextToImgStableDiffusion(text));
    }

    @PostMapping("/sd_full_param")
    public JsonResultVO<Boolean> requestTextToImg(@RequestBody SdFullRequestVO sdFullRequestVO) {
        return JsonResultVO.success(true);
    }

}
