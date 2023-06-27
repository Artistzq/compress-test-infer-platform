package com.kerbalogy.ctip.infer.controller;

import com.kerbalogy.ctip.infer.dto.InfoDTO;
import com.kerbalogy.ctip.infer.dto.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @author yaozongqing@eastmoney.com
 * @description
 * @date 2023-06-26
 **/
@RestController
@RequestMapping("/infer/cv/image_class")
public class ImageClassController {

    @PostMapping("one")
    public Result<InfoDTO> one(@RequestBody InfoDTO infoDTO) {
        InfoDTO info = InfoDTO.builder()
                .name(infoDTO.getName() + " processed")
                .type(infoDTO.getType())
                .build();
        return new Result<>(info, "666", 1);
    }

}
