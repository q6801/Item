package com.ssg.item.service.user;

import com.ssg.item.dto.user.UserDto;
import com.ssg.item.dto.user.UserResDto;
import com.ssg.item.entity.User;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.UserRepository;
import com.ssg.item.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResDto setUserInfo(UserDto userDto) {
        User user = User.convertDtoToUser(userDto);
        return userRepository.save(user).convertUserToResDto();
    }

    @Override
    @Transactional
    public void deleteUserInfo(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.USER_NOT_FOUND));
    }
}
