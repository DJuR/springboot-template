package com.jlearn.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dingjuru
 * @date 2021/12/7
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnum {
    /**
     * @AnonymousGetMapping
     */
    GET("GET"),

    /**
     * @AnonymousPostMapping
     */
    POST("POST"),

    /**
     * @AnonymousPutMapping
     */
    PUT("PUT"),

    /**
     * @AnonymousPatchMapping
     */
    PATCH("PATCH"),

    /**
     * 搜寻 @AnonymousDeleteMapping
     */
    DELETE("DELETE"),

    /**
     * 否则就是所有 Request 接口都放行
     */
    ALL("All");

    /**
     * Request 类型
     */
    private final String type;

    public static RequestMethodEnum find(String type) {
        for (RequestMethodEnum value : RequestMethodEnum.values()) {
            if (type.equals(value.getType())) {
                return value;
            }
        }
        return ALL;
    }
}
