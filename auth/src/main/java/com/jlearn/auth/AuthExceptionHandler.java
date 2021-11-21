package com.jlearn.auth;

import com.jlearn.BaseResponse;
import com.jlearn.exception.BadRequestException;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dingjuru
 * @date 2021/11/15
 */
@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity badCredentialsException(BadCredentialsException e) {

        String message = e.getMessage() != null ?  e.getMessage() : "BadCredentialsException异常" + e.toString();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {

        String message = e.getMessage() != null ? e.getMessage() : "Exception异常:" + e.toString();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestException(BadRequestException e) {

        String message = e.getMessage() != null ? e.getMessage() : "BadRequestException异常:" + e.toString();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClaimJwtException.class)
    public ResponseEntity expiredJwtException(ClaimJwtException e) {

        String message = e.getMessage() != null ? e.getMessage() : "ExpiredJwtException异常:" + e.toString();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity throwable(Throwable e) {

        String message = e.getMessage() != null ? e.getMessage() : "Throwable异常:" + e.toString();
        log.error(message);
        return new ResponseEntity(BaseResponse.error( 400, message), HttpStatus.BAD_REQUEST);
    }
}
