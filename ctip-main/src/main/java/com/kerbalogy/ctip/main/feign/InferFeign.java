package com.kerbalogy.ctip.main.feign;

import com.kerbalogy.ctip.main.dto.InfoDTO;
import com.kerbalogy.ctip.main.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-26
 **/
@FeignClient("infer-service")
public interface InferFeign {

    @PostMapping("/infer/cv/image_class/one")
    public Result<InfoDTO> call(@RequestBody InfoDTO infoDTO);

}
