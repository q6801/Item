package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.UserDto;
import com.ssg.item.dto.UserResDto;
import com.ssg.item.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("user")
    public ApiResult<UserResDto> setUserInfo(@Valid @RequestBody UserDto userDto) {
        UserResDto userResDto = userService.setUserInfo(userDto);
        return ApiProvider.success(userResDto);
    }

    @DeleteMapping("user/{userId}")
    public ApiResult<?> deleteUserInfo(@PathVariable long userId) {
        userService.deleteUserInfo(userId);
        return ApiProvider.success();
    }
}
