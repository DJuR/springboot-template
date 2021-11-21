package com.jlearn.controllers;

import com.jlearn.annotation.rest.AnonymousGetMapping;
import com.jlearn.repository.UserRepository;
import com.jlearn.utils.AuthUtils;
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
public class TestController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/test")
    public Object test() {

        UserDetails currentUser = AuthUtils.getCurrentUser();
        System.out.println(currentUser.getUsername());
        System.out.println(AuthUtils.getCurrentUserId());
        return  userRepository.findByUsername(currentUser.getUsername());
    }

    @GetMapping(value = "/test2")
    //@PreAuthorize("hasAuthority('test')")
    @PreAuthorize("hasRole('t_test')")
    public Object test2() {


        return  "test2 ok";
    }

    @GetMapping(value = "/test3")
    @PreAuthorize("hasAuthority('test')")
    public Object test3() {


        return  "test3 ok";
    }
}
