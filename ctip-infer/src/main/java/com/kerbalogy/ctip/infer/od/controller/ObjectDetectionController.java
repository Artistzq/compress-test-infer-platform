package com.kerbalogy.ctip.infer.od.controller;

import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@eastmoney.com
 * @description 物体检测
 * @date 2023-07-06
 **/
@RestController
@RequestMapping("/infer/od")
public class ObjectDetectionController {

    @PostMapping("/image")
    public JsonResultVO<?> postImage( ) {
        return JsonResultVO.success();
    }

}
