package com.jlearn.service.impl;

import com.jlearn.auth.TokenProvider;
import com.jlearn.config.properties.JwtProperties;
import com.jlearn.service.AuthService;
import com.jlearn.service.AuthUser;
import com.jlearn.service.AuthUserService;
import com.jlearn.service.dto.AuthLoginDTO;
import com.jlearn.service.dto.AuthUserDTO;
import com.jlearn.utils.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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

        String currentUsername = AuthUtils.getCurrentUsername();
        String token = tokenProvider.getToken(request);

        return new AuthUserDTO(authUserService.findByUsername(currentUsername),
                properties.getTokenStartWith() + token,
                authUserService.getAuthorities(currentUsername));
    }
}
