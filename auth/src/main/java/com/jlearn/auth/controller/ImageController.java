package com.jlearn.auth.controller;

import com.jlearn.auth.annotation.rest.AnonymousGetMapping;
import com.jlearn.auth.utils.ImageUtil;
import com.jlearn.common.BaseResponse;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

/**
 * @author dingjuru
 * @date 2021/12/7
 */
@RestController
@RequestMapping("/auth/image/")
@AllArgsConstructor
@Slf4j
public class ImageController {

    @Operation(summary = "验证码", tags = {"AUTH"})
    @AnonymousGetMapping("/code")
    public BaseResponse code(HttpServletResponse response) throws IOException {


        BufferedImage out = ImageUtil.getImage(200, 30, BufferedImage.TYPE_3BYTE_BGR)
                .backColor(ImageUtil.getRandomColor())
                .drawLine(4)
                //.drawPoint(0.05f)
                .drawString("ancd")
                .out();

        response.setContentType("image/jpeg");

        ImageIO.write(out, "JPEG", response.getOutputStream());
        return null;
    }


    @Operation(summary = "captcha", tags = {"AUTH"})
    @AnonymousGetMapping("/captcha")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.text().toLowerCase();

        String text = captcha.text();
        System.out.println(text);


        CaptchaUtil.out(captcha, request, response);

    }
}
