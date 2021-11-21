package com.jlearn.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author dingjuru
 * @date 2021/11/19
 */
public interface UserService {

    /**
     * 通过账号获取用户信息
     * @param username
     * @return
     */
    AuthUser findByUsername(String username);

    /**
     * 通过账号获取用户权限&角色
     * @param username
     * @return
     */
    Collection<GrantedAuthority> getAuthorities(String username);

}
