package com.ssg.item.service;

import com.ssg.item.dto.UserDto;
import com.ssg.item.dto.UserResDto;
import com.ssg.item.entity.User;
import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import com.ssg.item.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Test
    @DisplayName("setUserDto 성공")
    public void setUserDto() {
        UserDto inputData = new UserDto("name", UserType.NORMAL_USER, UserStat.NORMAL);
        User user = User.convertDtoToUser(inputData);
        User savedUser = new User(1, "name", UserType.NORMAL_USER, UserStat.NORMAL);
        BDDMockito.given(userRepository.save(user)).willReturn(savedUser);

        UserResDto savedUserDto = userService.setUserInfo(inputData);

        assertThat(savedUserDto).isEqualTo(savedUser.convertUserToResDto());
    }
}
