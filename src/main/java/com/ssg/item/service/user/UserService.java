package com.ssg.item.service.user;

import com.ssg.item.dto.user.UserDto;
import com.ssg.item.dto.user.UserResDto;
import com.ssg.item.entity.User;

public interface UserService {
    UserResDto setUserInfo(UserDto userDto);
    void deleteUserInfo(long userId);
    User findById(long id);
}
