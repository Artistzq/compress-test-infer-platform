package com.kerbalogy.ctip.auth.service;

import com.kerbalogy.ctip.auth.dto.UserDTO;
import com.kerbalogy.ctip.auth.entity.SecurityUser;
import com.nimbusds.jose.util.ArrayUtils;
import jakarta.annotation.PostConstruct;
import org.apache.http.config.MessageConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaozongqing@outlook.com
 * @date 2023/7/9 11:50
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    private List<UserDTO> userList;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new UserDTO(1L, "yao", password, 1, new ArrayList<>(List.of("ADMIN"))));
        userList.add(new UserDTO(2L, "song", password, 1, new ArrayList<>(List.of("TEST"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> findUserList = userList.stream()
                .filter(item -> item.getUsername().equals(username))
                .toList();
        if (findUserList.size() == 0) {
            throw new UsernameNotFoundException("1");
        }

        SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (! securityUser.isEnabled()) {
            throw new DisabledException("2");
        } else if (! securityUser.isAccountNonLocked()) {
            throw new LockedException("3");
        } else if (! securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException("4");
        } else if (! securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("5");
        }

        return securityUser;
    }
}
