package com.kerbalogy.ctip.infer.od.feign;

import com.kerbalogy.ctip.infer.od.dto.ObjectDetectArgs;
import com.kerbalogy.ctip.infer.od.dto.OdFeignRequest;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@FeignClient("object-detect-service")
public interface ObjectDetectFeign {

    @PostMapping("/infer/od/yolov8_request_body")
    byte[] requestOdResult(@RequestBody OdFeignRequest request);

    @PostMapping("/infer/od/yolov8_bytes")
    byte[] requestOdResult(@RequestParam("bytes") byte[] image, @RequestParam("args") ObjectDetectArgs objectDetectArgs);

    @PostMapping(path = "/infer/od/yolov8")
    @Headers("Content-Type: multipart/form-data")
    byte[] requestOdResult(@Param("fileb") MultipartFile file, @Param("args") String objectDetectArgs);

}
