package com.jlearn.auth.auth;

import com.alibaba.fastjson.JSONObject;
import com.jlearn.common.BaseResponse;
import com.jlearn.auth.config.properties.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
public class TokenFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    private final TokenProvider tokenProvider;
    private final JwtProperties properties;

    /**
     *
     * @param tokenProvider
     * @param properties
     */
    public TokenFilter(TokenProvider tokenProvider, JwtProperties properties) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);

        if(!StringUtils.isEmpty(token)) {

            Authentication authentication = null;
            try {
                authentication = tokenProvider.getAuthentication(token);
            } catch (Exception e) {
                e.printStackTrace();

                // doFilter异常返回
                BaseResponse error = BaseResponse.error(403, e.getMessage());
                servletResponse.setCharacterEncoding("UTF-8");
                servletResponse.setContentType("application/json");
                servletResponse.getWriter().println(JSONObject.toJSON(error));
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.debug("非法的Token: {}", bearerToken);
        }

        return null;
    }


}
