package com.jlearn.auth.service.dto;

import com.alibaba.fastjson.JSONObject;
import com.jlearn.auth.service.AuthUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 返回用户登录后信息
 *
 * @author dingjuru
 * @date 2021/11/12
 */
@Getter
public class AuthUserDTO  {

    /**
     * 用户信息
     */
    private final AuthUser user;

    /**
     * 认证token
     */
    private String token;

    /**
     * 权限&角色
     */
    private Collection<GrantedAuthority> authorities;

    public AuthUserDTO(AuthUser user, String token, Collection<GrantedAuthority> authorities) {
        this.user = user;
        this.token = token;
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }


    /**
     * 更改tokne
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
