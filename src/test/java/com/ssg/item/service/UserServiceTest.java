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

import java.util.ArrayList;
import java.util.List;

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
        User user = getStubUsers().get(0);
        UserDto inputData = new UserDto(user.getName(), user.getUserType(), user.getUserStat());
        BDDMockito.given(userRepository.save(user)).willReturn(user);

        UserResDto savedUserDto = userService.setUserInfo(inputData);

        assertThat(savedUserDto).isEqualTo(user.convertUserToResDto());
    }

    private List<User> getStubUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(0, "name0", UserType.NORMAL_USER, UserStat.NORMAL));
        users.add(new User(1, "name1", UserType.CORPORATE_USER, UserStat.WITHDRAWAL));
        return users;
    }

}
