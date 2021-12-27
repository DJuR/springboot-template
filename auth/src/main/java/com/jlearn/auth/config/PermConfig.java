package com.jlearn.auth.config;

import com.jlearn.auth.config.properties.LoginProperties;
import com.jlearn.auth.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证
 *
 * @author dingjuru
 * @date 2021/11/17
 */
@Service(value = "perm")
@RequiredArgsConstructor
public class PermConfig {

    private final LoginProperties loginProperties;

    public Boolean check(String ...perms) {
        // 获取当前用户权限
        List<String> authorities =
                AuthUtil.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return authorities.contains(loginProperties.getRolePrefix()+"admin") || Arrays.stream(perms).anyMatch(authorities::contains);
    }
}
