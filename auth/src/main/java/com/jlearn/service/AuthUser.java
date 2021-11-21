package com.jlearn.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息结构
 *
 * @author dingjuru
 * @date 2021/11/12
 */

public abstract class AuthUser {

    private Auth auth;

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthUser() {
        auth = new Auth();
    }

    @JSONField(serialize = false)
    public Auth getAuth() {
        return auth;
    }

    public void setAuthEnabled(boolean enabled) {
        auth.setEnabled(enabled);
    }

    @JSONField(serialize = false)
    public Boolean getAuthEnabled() {
        return auth.getEnabled();
    }

    @JSONField(serialize = false)
    public Boolean getAuthAccountNonExpired() {
        return auth.getAccountNonExpired();
    }

    @JSONField(serialize = false)
    public Boolean getAuthCredentialsNonExpired() {
        return auth.getCredentialsNonExpired();
    }

    @JSONField(serialize = false)
    public Boolean getAuthAccountNonLocked() {
        return auth.getAccountNonLocked();
    }

    @Getter
    @Setter
    private static class Auth {

        private Boolean enabled = true;

        private  Boolean accountNonExpired = true;

        private  Boolean credentialsNonExpired = true;

        private  Boolean accountNonLocked = true;

    }


}
