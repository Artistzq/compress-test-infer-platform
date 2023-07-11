package com.kerbalogy.ctip.auth.vanilla.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-07-11
 * @description
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserLogoutStatusDTO {

    private String message;

}
