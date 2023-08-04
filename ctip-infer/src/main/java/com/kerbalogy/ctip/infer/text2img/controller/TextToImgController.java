package com.kerbalogy.ctip.infer.text2img.controller;

import com.kerbalogy.ctip.infer.text2img.dto.StableDiffusionParamDTO;
import com.kerbalogy.ctip.infer.text2img.service.TextToImgService;
import com.kerbalogy.ctip.infer.text2img.vo.SdFullRequestVO;
import com.kerblogy.ctip.common.kafka.KafkaMessage;
import com.kerblogy.ctip.common.kafka.KafkaMessageCode;
import com.kerblogy.ctip.common.kafka.KafkaMessageResult;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JsonResultVO<Boolean> requestTextToImg(@RequestParam String text) {
        return requestTextToImg(SdFullRequestVO.builder()
                .prompt(text)
                .build());
    }

    @PostMapping("/sd_full_param")
    public JsonResultVO<Boolean> requestTextToImg(@RequestBody SdFullRequestVO sdFullRequestVO) {
        StableDiffusionParamDTO paramDTO = new StableDiffusionParamDTO();
        BeanUtils.copyProperties(sdFullRequestVO, paramDTO);
        Boolean success = textToImgService.requestTextToImgStableDiffusion(paramDTO);
        if (success) {
            return JsonResultVO.success(true, KafkaMessageResult.SEND_SUCCESS.getMsg());
        } else {
            return JsonResultVO.fail(KafkaMessageResult.SEND_FAIL.getMsg());
        }
    }

}
