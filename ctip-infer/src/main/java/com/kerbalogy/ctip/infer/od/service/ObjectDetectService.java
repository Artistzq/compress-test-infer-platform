package com.kerbalogy.ctip.infer.od.service;

import com.kerbalogy.ctip.infer.od.dto.ObjectDetectArgs;
import com.kerblogy.ctip.common.models.dto.ServiceResponse;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
public interface ObjectDetectService {
    byte[] requestOd(byte[] bytes, ObjectDetectArgs args);
}
