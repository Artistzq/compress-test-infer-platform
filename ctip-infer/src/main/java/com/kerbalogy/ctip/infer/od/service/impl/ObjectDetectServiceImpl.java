package com.kerbalogy.ctip.infer.od.service.impl;

import com.kerbalogy.ctip.infer.od.feign.ObjectDetectFeign;
import com.kerbalogy.ctip.infer.od.service.ObjectDetectService;
import com.kerblogy.ctip.common.models.dto.ServiceResponse;
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
    public ServiceResponse<String> requestOd() {
        ServiceResponse<String> response = objectDetectFeign.requestOdResult();
        // TODO：落库等处理

        return response;
    }
}
