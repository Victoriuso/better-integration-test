package io.victoriuso.better_integration_test.controller.advice;

import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorControllerHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<?> handleBadRequestException(ConstraintViolationException e) {
        log.error("error because bad request : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> handleNotFoundException(MethodArgumentNotValidException e) {
        log.error("error because request error : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .data(null)
                .build();
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            RuntimeException.class,
            IllegalArgumentException.class,
    })
    public BaseResponse<?> handleIamTeapotException(Exception e) {
        log.error("error 418 : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()))
                .status(HttpStatus.I_AM_A_TEAPOT.getReasonPhrase())
                .data(null)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("error because : {}", e.getMessage(), e);
        return BaseResponse.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(null)
                .build();
    }
}
