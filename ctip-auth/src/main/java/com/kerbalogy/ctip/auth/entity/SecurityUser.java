package com.kerbalogy.ctip.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-01
 * @description
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityUser implements UserDetails {

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
    private List<Role> roles;
    /**
     * 角色ID
     */
    private Integer roleId;

    public SecurityUser(LoginUser user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setEnabled(user.getEnabled());
        this.setRoleId(user.getRoleId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(item -> new SimpleGrantedAuthority(item.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
