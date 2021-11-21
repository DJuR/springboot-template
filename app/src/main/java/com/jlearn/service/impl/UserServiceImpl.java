package com.jlearn.service.impl;

import com.jlearn.domain.User;
import com.jlearn.exception.BadRequestException;
import com.jlearn.repository.UserRepository;
import com.jlearn.service.AuthUser;
import com.jlearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author dingjuru
 * @date 2021/11/19
 */
@Service
public class UserServiceImpl implements UserService {

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

        SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("test");
        list.add(auth1);

        return list;
    }



}
