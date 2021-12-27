package com.jlearn.auth.service.impl;

import com.jlearn.auth.service.AuthUserService;
import com.jlearn.auth.service.AuthUser;
import com.jlearn.auth.service.dto.AuthUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author dingjuru
 * @date 2021/11/12
 */

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthUserService authUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUserDTO jwtDTO = null;

        AuthUser user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        } else if(!user.getAuthEnabled()) {
            throw new DisabledException("账号未激活！");
        } else if(!user.getAuthAccountNonExpired()) {
            throw new DisabledException("账号已过期！");
        } else if(!user.getAuthCredentialsNonExpired()) {
            throw new DisabledException("凭证已过期！");
         } else if (!user.getAuthAccountNonLocked()) {
            throw new DisabledException("账号已锁定！");
        }

        return new User(user.getUsername(), user.getPassword(), user.getAuthEnabled(),
                user.getAuthAccountNonExpired(),
                user.getAuthCredentialsNonExpired(),
                user.getAuthAccountNonLocked(),
                authUserService.getAuthorities(username));
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public AuthUser findByUsername(String username) {
        return authUserService.findByUsername(username);

    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        return authUserService.getAuthorities(username);
    }
}
