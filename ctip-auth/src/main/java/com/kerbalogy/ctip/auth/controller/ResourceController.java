package com.kerbalogy.ctip.auth.controller;

import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-10
 * @description
 **/
@RestController
@RequestMapping("/api/resource/auth")
public class ResourceController {

    @PostMapping("/name")
    public JsonResultVO<?> name() {
        return JsonResultVO.success("测试受保护资源");
    }

}
