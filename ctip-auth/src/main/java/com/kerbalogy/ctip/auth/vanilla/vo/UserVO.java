package com.kerbalogy.ctip.auth.vanilla.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/10 22:21
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户账号")
public class UserVO {

    @NotBlank(message = "用户名不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, type = "String")
    private String username;

    @Size(min = 6, message = "密码不能少于6位")
    @NotBlank(message = "密码不能为空")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, type = "String")
    private String password;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "邮箱验证码", requiredMode = Schema.RequiredMode.REQUIRED, type = "String")
    private String code;

}
