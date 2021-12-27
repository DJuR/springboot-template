package com.jlearn.auth.config.properties;

import lombok.Data;

/**
 * 图片配置信息
 *
 * @author dingjuru
 * @date 2021/12/6
 */
@Data
public class CodeProperties {

    /**
     * 验证码有效期 分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;


}
