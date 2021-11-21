package com.jlearn.controller;

import com.jlearn.BaseResponse;
import com.jlearn.annotation.rest.AnonymousDeleteMapping;
import com.jlearn.annotation.rest.AnonymousPostMapping;
import com.jlearn.service.AuthService;
import com.jlearn.service.AuthUser;
import com.jlearn.service.dto.AuthLoginDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @AnonymousPostMapping(value = "/login")
    public BaseResponse login(@Validated @RequestBody AuthLoginDTO resource) throws Exception {

        return BaseResponse.success(authService.login(resource), "登录成功!");
    }

    @AnonymousDeleteMapping("/logout")
    public ResponseEntity<Object> logout() {
        authService.logout();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public BaseResponse<AuthUser> user(HttpServletRequest request) {

        return BaseResponse.success(authService.user(request), "用户信息！");
    }
}
