package com.jlearn.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 用返回用户信息
 * @author dingjuru
 * @date 2021/11/17
 */
@Service
public interface AuthUserService {

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    AuthUser findByUsername(String username);


    Collection<GrantedAuthority> getAuthorities(String username);




}
