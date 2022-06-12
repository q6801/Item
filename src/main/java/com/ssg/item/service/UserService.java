package com.ssg.item.service;

import com.ssg.item.dto.UserDto;
import com.ssg.item.dto.UserResDto;
import com.ssg.item.entity.User;

public interface UserService {
    UserResDto setUserInfo(UserDto userDto);
    void deleteUserInfo(long userId);
    User findById(long id);
}
