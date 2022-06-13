package com.ssg.item.dto.user;

import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class UserDto {
    @NotBlank(message = "유저의 이름은 빈 값일 수 없습니다.")
    private String name;

    @NotNull(message = "유저의 타입은 널값일 수 없습니다.")
    private UserType userType;

    @NotNull(message = "유저의 스탯은 널값일 수 없습니다.")
    private UserStat userStat;

    public UserDto(String name, UserType userType, UserStat userStat) {
        this.name = name;
        this.userType = userType;
        this.userStat = userStat;
    }
}
