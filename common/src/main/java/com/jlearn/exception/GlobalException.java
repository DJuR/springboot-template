package com.jlearn.exception;

import com.jlearn.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

   // @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseResponse> handleException(Throwable e) {

        return new ResponseEntity<>(BaseResponse.error(400, e.getMessage()), HttpStatus.valueOf(400));
    }
}
