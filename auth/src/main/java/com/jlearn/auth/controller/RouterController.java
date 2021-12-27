package com.jlearn.auth.controller;

import com.jlearn.common.BaseResponse;
import com.jlearn.auth.annotation.rest.AnonymousGetMapping;
import com.jlearn.auth.service.dto.RouterDTO;
import com.jlearn.auth.utils.SpringContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author dingjuru
 * @date 2021/11/22
 */
@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/auth/router/")
@AllArgsConstructor
public class RouterController {

    @AnonymousGetMapping("/index")
    public BaseResponse index(HttpServletRequest request) {

        List<RouterDTO> routerList = new ArrayList<>();

        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringContextHolder.getBean("requestMappingHandlerMapping",
                RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {

            RequestMappingInfo key = requestMappingInfoHandlerMethodEntry.getKey();
            HandlerMethod value = requestMappingInfoHandlerMethodEntry.getValue();

            ArrayList<RequestMethod> requestMethods = new ArrayList<>(key.getMethodsCondition().getMethods());

            if(requestMethods.size() > 0) {

                Operation operation = value.getMethodAnnotation(Operation.class);
                String summary = null;
                String description = null;

                if(!Objects.isNull(operation)) {
                    summary = operation.summary();
                    description = operation.description();
                }

                String method = requestMethods.get(0).name();

                Set<String> patterns = key.getPatternsCondition().getPatterns();
                for (String pattern : patterns) {
                    RouterDTO routerDTO = new RouterDTO();
                    routerDTO.setMethod(method);
                    routerDTO.setRouter(pattern);
                    routerDTO.setSummary(summary);
                    routerDTO.setDescription(description);

                    routerList.add(routerDTO);
                }
            }
        }

        return BaseResponse.success(routerList, "路由列表");
    }


}
