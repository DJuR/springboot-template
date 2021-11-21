package com.jlearn.service;

import com.jlearn.service.dto.AuthLoginDTO;
import com.jlearn.service.dto.AuthUserDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dingjuru
 * @date 2021/11/15
 */
@Service
public interface AuthService {

    Object login(AuthLoginDTO resource);

    void logout();

    AuthUserDTO user(HttpServletRequest request);
}
