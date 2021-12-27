package com.jlearn.common.exception;

import com.jlearn.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dingjuru
 * @date 2021/11/17
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsException(BadCredentialsException e) {

        String message = e.getMessage() != null ?  e.getMessage() : "BadCredentialsException异常" + e;
        e.printStackTrace();
        log.error("BadCredentialsException异常"+message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {

        String message = e.getMessage() != null ? e.getMessage() : "Exception异常:" + e;
        e.printStackTrace();
        log.error("Exception异常：" + e.toString());
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity badRequestException(BadRequestException e) {

        String message = e.getMessage() != null ? e.getMessage() : "BadRequestException异常:" + e;
        e.printStackTrace();
        log.error("BadRequestException异常：" + message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity throwable(Throwable e) {

        String message = e.getMessage() != null ? e.getMessage() : "Throwable异常:" + e.toString();
        e.printStackTrace();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }


}
