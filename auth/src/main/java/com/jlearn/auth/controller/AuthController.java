package com.jlearn.auth.controller;

import com.jlearn.auth.annotation.rest.AnonymousGetMapping;
import com.jlearn.common.BaseResponse;
import com.jlearn.auth.annotation.rest.AnonymousDeleteMapping;
import com.jlearn.auth.annotation.rest.AnonymousPostMapping;
import com.jlearn.auth.service.AuthService;
import com.jlearn.auth.service.AuthUser;
import com.jlearn.auth.service.dto.AuthLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
@Slf4j
@RestController
@RequestMapping("/auth/auth/")
@AllArgsConstructor
@Tag(name = "AUTH", description = "登录")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录", tags = {"AUTH"}, description = "用户登录")
    @AnonymousPostMapping(value = "/login")
    public BaseResponse login(@Validated @RequestBody AuthLoginDTO resource) throws Exception {

        return BaseResponse.success(authService.login(resource), "登录成功!");
    }

    @Operation(summary = "退出", tags = {"AUTH"})
    @AnonymousDeleteMapping("/logout")
    public ResponseEntity<Object> logout() {
        authService.logout();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "用户信息", tags = {"AUTH"})
    @GetMapping("/user")
    public BaseResponse<AuthUser> user(HttpServletRequest request) {

        return BaseResponse.success(authService.user(request), "用户信息！");
    }


    @Operation(summary = "验证码", tags = {"AUTH"})
    @AnonymousGetMapping("/code")
    public BufferedImage code(HttpServletResponse response) {

        BufferedImage image = new BufferedImage(10, 20, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(Color.white);
        g.fillRect(0, 0, 10, 20);

        return image;
    }
}
