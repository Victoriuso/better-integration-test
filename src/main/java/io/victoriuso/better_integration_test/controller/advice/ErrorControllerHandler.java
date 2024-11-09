package io.victoriuso.better_integration_test.controller.advice;

import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorControllerHandler {

    @ExceptionHandler(BadRequestException.class)
    public BaseResponse<?> handleBadRequestException(BadRequestException e) {
        log.error("error because bad request : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .build();

    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("error because : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .build();
    }
}
