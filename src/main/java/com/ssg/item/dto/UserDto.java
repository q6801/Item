package com.ssg.item.dto;

import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class UserDto {
    @NotBlank
    private String name;

    @NotNull
    private UserType userType;

    @NotNull
    private UserStat userStat;

    public UserDto(String name, UserType userType, UserStat userStat) {
        this.name = name;
        this.userType = userType;
        this.userStat = userStat;
    }
}
