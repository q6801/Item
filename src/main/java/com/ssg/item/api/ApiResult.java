package com.ssg.item.api;

import lombok.Getter;

@Getter
public class ApiResult<T> {
    private boolean success;
    private T message;
    private ApiError error;

    public ApiResult(boolean success, T message, ApiError error) {
        this.success = success;
        this.message = message;
        this.error = error;
    }
}
