package com.ssg.item.api;

import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;

public class ApiProvider {
    public static<T> ApiResult<T> success() {
        return new ApiResult<>(true, null, null);
    }

    public static<T> ApiResult<T> success(T message) {
        return new ApiResult<>(true, message, null);
    }

    public static<T> ApiResult<T> error(String errorCode, String errorMessage) {
        return new ApiResult<>(false, null,
                new ApiError(errorCode, errorMessage));
    }

    public static<T> ApiResult<T> error(CustomRuntimeException exception) {
        return error(exception.getCode(), exception.getMessage());
    }
}