package com.jlearn.auth.utils;

import com.alibaba.fastjson.JSONObject;
import com.jlearn.auth.service.AuthUser;
import com.jlearn.auth.service.AuthUserService;
import com.jlearn.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Slf4j
public class AuthUtil {

    /**
     * 获取当前登录用户bean
     * @return
     */
    public static AuthUserService getAuthUser() {
        return SpringContextHolder.getBean(AuthUserService.class);
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public static AuthUser getCurrentUser() {
        return getAuthUser().findByUsername(getCurrentUsername());
    }

    /**
     * 获取当前用户权限
     * @return
     */
    public static  Collection<GrantedAuthority> getAuthorities() {
        return getAuthUser().getAuthorities(getCurrentUsername());
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前用户登录已失效！");
        }

        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }

        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前用户未登录");
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public static Long getCurrentUserId() {
        return JSONObject.parseObject(JSONObject.toJSONString(getCurrentUser())).getObject("id", Long.class);
    }

}
