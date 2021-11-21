package com.jlearn;

import com.jlearn.utils.SpringContextHolder;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author dingjuru
 * @date 2021/11/17
 */

public class AuthTests {

    @Test
    void encode() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);
    }


}
