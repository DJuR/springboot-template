package com.jlearn.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author dingjuru
 * @date 2021/11/15
 */
@Data
public class BaseResponse<T extends Object> {

    private Integer code;
    private Object data = null;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private BaseResponse() {
        timestamp = LocalDateTime.now();
    }


    /**
     * 成功响应
     * @param data
     * @return
     */
    public static <T>  BaseResponse success(T data, String message) {
        return data(200, data, message);
    }

    /**
     * 错误响应
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(Integer code, String message) {
        return data(code, null, message);
    }

    /**
     * 响应
     *
     * @param code
     * @param message
     * @return
     */
    public static <T> BaseResponse data(Integer code,T data, String message) {
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setData(data);
        response.setMessage(message);
        return response;
    }
}
