package com.kerblogy.ctip.common.models.vo;

import com.kerblogy.ctip.common.enums.ResponseCode;
import com.kerblogy.ctip.common.util.web.HttpUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @description 前端响应类
 * @date 2023-07-05
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "前端响应类")
public class JsonResultVO <T> {

    @Schema(description = "响应是否成功")
    private boolean flag;

    @Schema(description = "响应状态码")
    private int code;

    @Schema(description = "响应描述")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "响应时间，精确到毫秒")
    private long time;

    @Schema(description = "请求id，用于debug，通过url传参url?debug=true时返回")
    private String requestId;

    public static <T> JsonResultVO<T> success() {
        return build(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), null);
    }

    public static <T> JsonResultVO<T> success(T data) {
        return build(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    public static <T> JsonResultVO<T> success(T data, String message) {
        return build(true, ResponseCode.SUCCESS.getCode(), message, data);
    }

    public static <T> JsonResultVO<T> fail() {
        return build(false, ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getDesc(), null);
    }

    public static <T> JsonResultVO<T> fail(ResponseCode responseCode) {
        return build(false, responseCode.getCode(), responseCode.getDesc(), null);
    }

    public static <T> JsonResultVO<T> fail(Integer code, String message) {
        return build(false, code, message, null);
    }

    public static <T> JsonResultVO<T> fail(String message) {
        return build(false,ResponseCode.FAIL.getCode(), message, null);
    }

    public static <T> JsonResultVO<T> fail(T data) {
        return build(false, ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getDesc(), data);
    }

    public static <T> JsonResultVO<T> fail(T data, String message) {
        return build(false,ResponseCode.FAIL.getCode(), message, data);
    }

    private static <T> JsonResultVO<T> build(boolean flag, int code, String message, T data) {
        return JsonResultVO.<T>builder()
                .flag(flag)
                .code(code)
                .message(message)
                .data(data)
                .time(System.currentTimeMillis())
                .requestId((String) HttpUtil.getHttpServletRequest().getAttribute("_request_id"))
                .build();
    }
}
