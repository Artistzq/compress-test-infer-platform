package com.kerbalogy.ctip.infer.od.feign;

import com.kerblogy.ctip.common.models.dto.ServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@FeignClient("object-detect-service")
public interface ObjectDetectFeign {

    @PostMapping("/infer/od/yolov8")
    ServiceResponse<String> requestOdResult();

}
