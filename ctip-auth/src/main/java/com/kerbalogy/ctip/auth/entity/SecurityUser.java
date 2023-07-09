package com.kerbalogy.ctip.auth.entity;

import com.kerbalogy.ctip.auth.dto.UserDTO;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/9 12:01
 * @description
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    private Long id;

    private String username;

    private String password;

    private Boolean enabled;

    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserDTO userDTO) {
        this.setId(userDTO.getId());
        this.setUsername(userDTO.getUsername());
        this.setPassword(userDTO.getPassword());
        this.setEnabled(userDTO.getStatus() == 1);
        if (userDTO.getRoles() != null) {
            authorities = new ArrayList<>();
            userDTO.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
