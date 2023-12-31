package com.kerbalogy.ctip.auth.controller;

import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-07
 * @description
 **/
@RequestMapping("/test")
@RestController
public class HelloController {

    @GetMapping("/hello")
    public JsonResultVO<?> test() {
        return JsonResultVO.success("Hello");
    }

    @PostMapping("/post")
    public JsonResultVO<?> post() {
        return JsonResultVO.success("post 200");
    }

}
