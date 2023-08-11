package com.kerbalogy.ctip.infer.od.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-11
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdFeignRequest {
    byte[] image;
    ObjectDetectArgs objectDetectArgs;
}
