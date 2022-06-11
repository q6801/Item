package com.ssg.item.exception;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {

    @ExceptionHandler({CustomRuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> exceptionHandler(CustomRuntimeException ex) {
        log.error("{} : {}" , ex.getCode(), ex.getMessage());
        return ApiProvider.error(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage());
            sb.append(System.lineSeparator());
        }
        return ApiProvider.error(ExceptionEnum.ARGUMENT_NOT_VALID.getCode(), sb.toString());
    }

}