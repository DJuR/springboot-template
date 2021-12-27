package com.jlearn.auth.service.impl;

import com.jlearn.auth.auth.TokenProvider;
import com.jlearn.auth.config.properties.JwtProperties;
import com.jlearn.auth.service.AuthService;
import com.jlearn.auth.service.AuthUserService;
import com.jlearn.auth.service.dto.AuthLoginDTO;
import com.jlearn.auth.service.dto.AuthUserDTO;
import com.jlearn.auth.utils.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dingjuru
 * @date 2021/11/15
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final JwtProperties properties;
    private final AuthUserService authUserService;

    @Override
    public Object login(AuthLoginDTO resource) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(resource.getUsername(),
                resource.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        final User user = (User)authentication.getPrincipal();

        AuthUserDTO userDTO = new AuthUserDTO(authUserService.findByUsername(user.getUsername()),
                properties.getTokenStartWith() + token,
                user.getAuthorities());

        return userDTO;
    }

    @Override
    public void logout() {

    }

    @Override
    public AuthUserDTO user(HttpServletRequest request) {

        String currentUsername = AuthUtil.getCurrentUsername();
        String token = tokenProvider.getToken(request);

        return new AuthUserDTO(authUserService.findByUsername(currentUsername),
                properties.getTokenStartWith() + token,
                authUserService.getAuthorities(currentUsername));
    }

    @Override
    public void code() {

    }
}
