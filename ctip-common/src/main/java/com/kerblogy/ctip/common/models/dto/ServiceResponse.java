package com.kerblogy.ctip.common.models.dto;

import com.kerblogy.ctip.common.enums.ServiceSourceEnum;
import com.kerblogy.ctip.common.models.vo.JsonResultVO;
import com.kerblogy.ctip.common.util.web.HttpUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author yaozongqing@outlook.com
 * @description 内部服务的响应类
 * @date 2023-07-05
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "内部服务响应类")
public class ServiceResponse <T> extends JsonResultVO <T> {

    @Schema(description = "服务来源")
    private String serviceSource;

    @Builder(builderMethodName = "serviceResponseBuilder")
    public ServiceResponse(boolean flag, int code, String message, T data, long time, String requestId, String serviceSource) {
        super(flag, code, message, data, time, requestId);
        this.serviceSource = serviceSource;
    }

    private static <T> JsonResultVO<T> from(ServiceResponse<T> serviceResponse, ServiceSourceEnum serviceSource) {
        serviceResponse.setServiceSource(serviceSource.getSource());
        return serviceResponse;
    }

    private static <T> JsonResultVO<T> build(boolean flag, int code, String message, T data) {
        return ServiceResponse.<T>serviceResponseBuilder()
                .serviceSource(ServiceSourceEnum.SPRING.getSource())
                .flag(flag)
                .code(code)
                .message(message)
                .data(data)
                .time(System.currentTimeMillis())
                .requestId((String) HttpUtil.getHttpServletRequest().getAttribute("_request_id"))
                .build();
    }

}
