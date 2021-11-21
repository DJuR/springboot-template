package com.jlearn.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dingjuru
 * @date 2021/11/12
 */
@Getter
@AllArgsConstructor
public enum RequestMethodEnum {

    /**
     * @AnonymousGetMapping
     */
    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    PATCH("PATCH"),

    DETELE("DETELE"),

    ALL("ALL");

    private final String type;

    public static RequestMethodEnum find(String type) {
        for (RequestMethodEnum value : RequestMethodEnum.values()) {
            if(type.equals(value.getType())) {
                return value;
            }
        }

        return ALL;
    }

}
