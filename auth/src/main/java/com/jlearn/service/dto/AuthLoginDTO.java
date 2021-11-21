package com.jlearn.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjuru
 * @date 2021/11/18
 */
@Getter
@Setter
public class AuthLoginDTO {
    private String username;

    @JSONField(serialize = false)
    private String password;
}
