package com.kerbalogy.ctip.infer.od.feign;

import com.kerbalogy.ctip.infer.od.dto.ObjectDetectArgs;
import com.kerblogy.ctip.common.models.dto.ServiceResponse;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@FeignClient("object-detect-service")
public interface ObjectDetectFeign {

    @PostMapping("/infer/od/yolov8")
    byte[] requestOdResult(@RequestParam("bytes") byte[] image, @RequestParam("args") ObjectDetectArgs objectDetectArgs);

}
