package com.kerbalogy.ctip.main.infer.feign;

import com.kerbalogy.ctip.main.infer.dto.InfoDTO;
import com.kerbalogy.ctip.main.infer.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yaozongqing@outlook.com
 * @description
 * @date 2023-06-26
 **/
@FeignClient("infer-service")
public interface InferFeign {

    @PostMapping("/infer/cv/image_class/one")
    public Result<InfoDTO> call(@RequestBody InfoDTO infoDTO);

}
