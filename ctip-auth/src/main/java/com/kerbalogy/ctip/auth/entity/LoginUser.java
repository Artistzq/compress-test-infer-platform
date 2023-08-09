package com.kerbalogy.ctip.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-08
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private List<OldRole> oldRoles;
    /**
     * 角色ID
     */
    private Integer roleId;

}
