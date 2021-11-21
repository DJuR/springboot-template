package com.jlearn.config;

import com.jlearn.annotation.AnonymousAccess;
import com.jlearn.auth.JwtAccessDeniedHandler;
import com.jlearn.auth.JwtAuthEntryPoint;
import com.jlearn.auth.TokenConfigurer;
import com.jlearn.auth.TokenProvider;
import com.jlearn.config.properties.JwtProperties;
import com.jlearn.utils.SpringContextHolder;
import com.jlearn.utils.enums.RequestMethodEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * security配置
 *
 * @author dingjuru
 * @date 2021/11/12
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AuthConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationContext applicationContext;
    private final CorsFilter corsFilter;
    private JwtAuthEntryPoint jwtAuthenticationEntryPoint;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    /**
     * 设置角色前缀 ROLE_
     * @return
     */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("ROLE_");
    }

    /**
     * 密码加密方式bean
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     *
     * @return
     */
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // 搜寻匿名标记 url： @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean(
                "requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        // 获取匿名标记
        Map<String, Set<String>> anonymousUrls = getAnonymousUrls(handlerMethods);

        httpSecurity
                // 禁用CSRF
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                // 防止跨域访问
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建回话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 静态资源
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "webSocket/**"
                ).permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 匿名get
                .antMatchers(HttpMethod.GET,
                        anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
                // 匿名post
                .antMatchers(HttpMethod.POST,
                        anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and().apply(tokenConfigurer());

    }

    private TokenConfigurer tokenConfigurer() {
        return new TokenConfigurer(tokenProvider, jwtProperties);
    }

    private Map<String, Set<String>> getAnonymousUrls(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        HashMap<String, Set<String>> anonymousUrls = new HashMap<>(6);
        HashSet<String> get = new HashSet<>();
        HashSet<String> post = new HashSet<>();
        HashSet<String> put = new HashSet<>();
        HashSet<String> patch = new HashSet<>();
        HashSet<String> delete = new HashSet<>();
        HashSet<String> all = new HashSet<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if(null != anonymousAccess) {
                ArrayList<RequestMethod> requestMethods =
                        new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(requestMethods.size() == 0 ?
                        RequestMethodEnum.ALL.getType() :
                        requestMethods.get(0).name());

                switch (Objects.requireNonNull(request)) {
                    case GET:
                        get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case POST:
                        post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PUT:
                        put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case PATCH:
                        patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    case DETELE:
                        delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                    default:
                        all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                        break;
                }
            }
        }

        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DETELE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);

        return anonymousUrls;
    }
}
