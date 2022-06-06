package com.ssg.item.exception;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomRuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> exceptionHandler(CustomRuntimeException ex) {
        log.error("{} : {}" , ex.getExceptionEnum(), ex.getMessage());
        return ApiProvider.error(ex);
    }

}