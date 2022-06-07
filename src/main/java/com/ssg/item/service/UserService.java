package com.ssg.item.service;

import com.ssg.item.dto.UserDto;
import com.ssg.item.dto.UserResDto;

public interface UserService {
    UserResDto setUserInfo(UserDto userDto);
    void deleteUserInfo(long userId);
}
