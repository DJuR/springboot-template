package com.jlearn.service.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.jlearn.service.AuthUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 返回客户结果
 * @author dingjuru
 * @date 2021/11/12
 */
@Getter
public class AuthUserDTO  {

    /**
     * 用户信息
     */
    private final AuthUser user;

    private String token;

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
