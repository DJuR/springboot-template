package com.jlearn.auth.service.dto;

import com.alibaba.fastjson.JSONObject;

import java.security.Permission;
import java.util.List;

/**
 * 用户权限结构
 * @author dingjuru
 * @date 2021/11/18
 */
public class AuthPermDTO {


    private List<Permission> permissions;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
