package com.kerbalogy.ctip.main.controller;

import com.kerbalogy.ctip.main.dto.Result;
import com.kerbalogy.ctip.main.dto.InfoDTO;
import com.kerbalogy.ctip.main.feign.InferFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-26
 **/
@RestController
public class InferController {

    @Autowired
    private InferFeign inferFeign;

    @PostMapping("/nacos-test")
    public Result<InfoDTO> consume(@RequestBody InfoDTO infoDTO) {
        Result<InfoDTO> res = inferFeign.call(infoDTO);
//        System.out.println(res);
        res.setMsg("nacos-remote");
        return res;
    }

    @GetMapping("/local-test")
    public Result<String> c() {
        return new Result<String>("test", "666",  11);
    }

}
