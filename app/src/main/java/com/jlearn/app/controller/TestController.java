package com.jlearn.app.controller;

import com.jlearn.app.repository.UserRepository;
import com.jlearn.auth.annotation.rest.AnonymousGetMapping;
import com.jlearn.auth.service.AuthUser;
import com.jlearn.auth.service.AuthUserService;
import com.jlearn.auth.utils.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dingjuru
 * @date 2021/11/16
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    private UserRepository userRepository;


    /**
     * 是否有test权限
     * @return
     */
    @PreAuthorize("hasAuthority('test')")
    @GetMapping(value = "/test")
    public Object test() {

        AuthUser currentUser = AuthUtil.getCurrentUser();

        log.info("用户信息: {}", currentUser);

        log.info("用户userId: {}", AuthUtil.getCurrentUserId());

        String currentUsername = AuthUtil.getCurrentUsername();

        return  userRepository.findByUsername(currentUsername);
    }

    /**
     * 是否有test角色
     * @return
     */
    @PreAuthorize("hasRole('ROLE_test')")
    @GetMapping(value = "/test2")
    public Object test2() {


        return  "test2 ok";
    }

    @GetMapping(value = "/test3")
    @PreAuthorize("hasAuthority('test')")
    public Object test3() {

        return  "test3 ok";
    }

    @GetMapping(value = "/test4")
    @PreAuthorize("@perm.check('test4')")
    public Object test4() {

        return  "test4 ok";
    }
}
