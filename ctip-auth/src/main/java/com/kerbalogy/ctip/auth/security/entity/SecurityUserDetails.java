package com.kerbalogy.ctip.auth.security.entity;

import com.kerbalogy.ctip.auth.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
public class SecurityUserDetails implements UserDetails {

    private final User user;

    private final List<Long> roleIds;

    public User getUser() {
        return user;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public SecurityUserDetails(User user, List<Long> roleIds) {
        this.user = user;
        this.roleIds = roleIds;
    }

    public static SecurityUserDetails getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof SecurityUserDetails) {
            return (SecurityUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roleIds.forEach(roleId -> authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(String.valueOf(roleId))));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true;
    }
}
