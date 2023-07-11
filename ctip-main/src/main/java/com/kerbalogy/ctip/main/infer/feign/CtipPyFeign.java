package com.kerbalogy.ctip.main.infer.feign;

import com.kerbalogy.ctip.main.infer.dto.MsgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yaozongqing@outlook.com
 * @description
 * @date 2023-06-27
 **/
@FeignClient("ctip-py-service")
public interface CtipPyFeign {

    @PostMapping("/talk/infer")
    public MsgDTO test(@RequestBody MsgDTO msgDTO);

}
