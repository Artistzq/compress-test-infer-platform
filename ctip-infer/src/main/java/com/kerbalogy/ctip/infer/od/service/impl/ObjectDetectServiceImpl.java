package com.kerbalogy.ctip.infer.od.service.impl;

import com.kerbalogy.ctip.infer.od.dto.ObjectDetectArgs;
import com.kerbalogy.ctip.infer.od.dto.OdFeignRequest;
import com.kerbalogy.ctip.infer.od.feign.ObjectDetectFeign;
import com.kerbalogy.ctip.infer.od.service.ObjectDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Service
public class ObjectDetectServiceImpl implements ObjectDetectService {

    @Autowired
    ObjectDetectFeign objectDetectFeign;

    @Override
    public byte[] requestOd(byte[] bytes, ObjectDetectArgs args) {
        return objectDetectFeign.requestOdResult(OdFeignRequest.builder()
                .image(bytes)
                .objectDetectArgs(args)
                .build());
    }
}
