package com.jlearn.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.jlearn.service.AuthUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends AuthUser {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private Boolean enabled;

    private String sex = "男";

    private String nickname;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }
}
