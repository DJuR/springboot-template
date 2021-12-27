package com.jlearn.auth.auth;

import com.jlearn.auth.config.properties.JwtProperties;
import com.jlearn.common.exception.BadRequestException;
import com.jlearn.auth.service.AuthUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
@Component
public class TokenProvider implements InitializingBean {

    private final JwtProperties jwtProperties;
    public static final String AUTHORITIES_KEY = "user";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;
    private AuthUserService authUserService;


    public TokenProvider(JwtProperties jwtProperties, AuthUserService authUserService) {
        this.jwtProperties = jwtProperties;
        this.authUserService = authUserService;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);

        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     * 创建Token
     * @param authentication
     * @return
     */
    public String createToken(Authentication authentication) {
        return jwtBuilder
                .setId(UUID.randomUUID().toString())
                .claim(AUTHORITIES_KEY, authentication.getName())
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()) )
                .compact();
    }

    /**
     * 获取鉴权信息
     * @param token
     * @return
     */
    Authentication getAuthentication(String token)  {
        Claims claims = getClaims(token);
        if(claims.getExpiration().getTime() < System.currentTimeMillis()) {
            new BadRequestException(HttpStatus.BAD_REQUEST, "当前账户已过期!");
        }

        String username = claims.getSubject();
        Collection<GrantedAuthority> authorities = authUserService.getAuthorities(username);

        User principal = new User(username, "******", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Claims getClaims(String token) {
        try{
            return jwtParser.parseClaimsJws(token).getBody();
        }catch (Exception e)  {
            e.printStackTrace();
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "解析token异常: " + e.getMessage());
        }
    }


    /**
     * 获取token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        final String header = request.getHeader(jwtProperties.getHeader());
        if(header != null && header.startsWith(jwtProperties.getTokenStartWith())) {
            return header.substring(7);
        }

        return null;
    }
}
