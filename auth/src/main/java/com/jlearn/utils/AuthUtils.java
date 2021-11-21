package com.jlearn.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jlearn.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Slf4j
public class AuthUtils {

    /**
     * 获取当前登录用户
     * @return
     */
    public static UserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(getCurrentUsername());
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
        UserDetails userDetails = getCurrentUser();

        Object user = JSONObject.parseObject(JSONObject.toJSONString(userDetails)).get("user");
        return JSONObject.parseObject(JSONObject.toJSONString(user)).getObject("id", Long.class);
    }


}
