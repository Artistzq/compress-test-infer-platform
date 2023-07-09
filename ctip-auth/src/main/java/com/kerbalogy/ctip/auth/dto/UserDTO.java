package com.kerbalogy.ctip.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/9 11:54
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private Integer status;

    private List<String> roles;
}
