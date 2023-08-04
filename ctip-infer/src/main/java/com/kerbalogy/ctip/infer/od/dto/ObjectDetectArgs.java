package com.kerbalogy.ctip.infer.od.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-03
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectDetectArgs {

    int id;

    String name;

}
