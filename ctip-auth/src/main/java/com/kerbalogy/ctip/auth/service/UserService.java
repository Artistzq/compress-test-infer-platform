package com.kerbalogy.ctip.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kerbalogy.ctip.auth.entity.User;
import com.kerbalogy.ctip.auth.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-09
 * @description
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    public Page<User> page(Long currentPage, Long pageSize, String username) {
        Page<User> page = new Page<User>(currentPage, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(StringUtils.hasText(username), User::getUsername, username);
        return super.page(page, queryWrapper);
    }

    public Long add(User user) {
        user.setId(null);
        if (this.checkUsernameExist(user)) {
            throw new RuntimeException("用户名已存在");
        }
        if (this.checkPhoneExist(user)) {
            throw new RuntimeException("手机号已存在");
        }

        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);
        super.save(user);
        return user.getId();
    }

    public void update(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("id不为空");
        }
        if (this.checkUsernameExist(user)) {
            throw new RuntimeException("用户名已存在");
        }
        if (this.checkPhoneExist(user)) {
            throw new RuntimeException("手机号已存在");
        }

        user.setPassword(null);
        super.updateById(user);
    }

    public void updatePassword(String id, String oldPassword, String newPassword) {
        User user = super.getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(oldPassword,user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        String encryptPassword = passwordEncoder.encode(newPassword);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .eq(User::getId, id)
                .set(User::getPassword, encryptPassword);
        super.update(updateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setRole(Long id, Long[] roleIds) {
        userRoleService.deleteByUserId(id);
        userRoleService.add(id,roleIds);
    }

    private boolean checkUsernameExist(User user) {
        User exist = getByUsername(user.getUsername());
        if (user.getId() != null) {
            return exist != null && !user.getId().equals(exist.getId());
        }
        return exist != null;
    }

    private boolean checkPhoneExist(User user) {
        User exist = getByPhone(user.getPhone());
        if (user.getId() != null) {
            return exist != null && !user.getId().equals(exist.getId());
        }
        return exist != null;
    }

    public User getByUsername(String username) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getUsername, username);
        return this.getOne(qw);
    }

    public User getByPhone(String phone) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getPhone, phone);
        return this.getOne(qw);
    }

}
