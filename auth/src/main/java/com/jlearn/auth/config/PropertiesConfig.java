package com.jlearn.auth.config;

import com.jlearn.auth.config.properties.JwtProperties;
import com.jlearn.auth.config.properties.LoginProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义参数对象配置
 *
 * @author dingjuru
 * @date 2021/11/12
 *
 */
@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "login")
    public LoginProperties loginProperties() {
        return new LoginProperties();
    }
    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties securityProperties() {
        return new JwtProperties();
    }
}
