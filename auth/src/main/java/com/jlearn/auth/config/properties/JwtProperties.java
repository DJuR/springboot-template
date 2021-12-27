package com.jlearn.auth.config.properties;

import lombok.Data;

/**
 * JWT 参数配置
 * @author dingjuru
 * @date 2021/11/12
 */
@Data
public class JwtProperties {

    private String header;

    /**
     * 令牌前缀 Bearer
     */
    private String tokenStartWith;

    /**
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String base64Secret;

    /**
     * 令牌过期时间 此处单位/毫秒
     */
    private Long tokenValidityInSeconds;

    /**
     * 在线用户 key，根据 key 查询 redis 中在线用户的数据
     */
    private String onlineKey;

    /**
     * 验证码 key
     */
    private String codeKey;

    /**
     * token 续期检查
     */
    private Long detect;

    /**
     * 续期时间
     */
    private Long renew;

    /**
     * 过期时间
     */
    private Long expiration;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
