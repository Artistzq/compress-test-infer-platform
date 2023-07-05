package com.kerblogy.ctip.common.models.vo;

import com.kerblogy.ctip.common.models.enums.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@eastmoney.com
 * @description 前端响应类
 * @date 2023-07-05
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "前端响应类")
public class JsonResultVO <T> {

    @Schema(description = "响应状态码")
    private int code;

    @Schema(description = "响应描述")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "响应时间，精确到毫秒")
    private long time = System.currentTimeMillis();

    @Schema(description = "请求id，用于debug，通过url传参url?debug=true时返回")
    private String requestId;



}
