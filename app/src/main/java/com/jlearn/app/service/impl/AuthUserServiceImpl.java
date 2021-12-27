package com.jlearn.app.service.impl;

import com.jlearn.app.domain.User;
import com.jlearn.app.repository.UserRepository;
import com.jlearn.auth.service.AuthUser;
import com.jlearn.auth.service.AuthUserService;
import com.jlearn.common.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 实现AuthUserService接口，实现获取用户信息和权限方法
 * @author dingjuru
 * @date 2021/11/17
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 通过账号获取用户信息
     * @param username
     * @return
     */
    @Override
    public AuthUser findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        // 用户不存在
        if(null == user) {
            throw new BadRequestException("用户名或密码错误！");
        }

        user.setAuthEnabled(user.getEnabled());
        return user;
    }

    /**
     * 通过账号获取权限
     * @param username
     * @return
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities(String username) {
        ArrayList<GrantedAuthority> list = new ArrayList<>();

        // 测试设置权限
        SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("test");
        list.add(auth1);

        SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("test2");
        list.add(auth2);

        // 测试设置角色
        SimpleGrantedAuthority auth3 = new SimpleGrantedAuthority("ROLE_test");
        list.add(auth3);


        return list;
    }
}
