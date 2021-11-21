package com.jlearn.service.impl;

import com.jlearn.service.AuthUser;
import com.jlearn.service.AuthUserService;
import com.jlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 实现AuthUserService结构，实现获取用户信息和权限方法
 * @author dingjuru
 * @date 2021/11/17
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    UserService userService;

    @Override
    public AuthUser findByUsername(String username) {
        return userService.findByUsername(username);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String username) {
        return userService.getAuthorities(username);
    }
}
