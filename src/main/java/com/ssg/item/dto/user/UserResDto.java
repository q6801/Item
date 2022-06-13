package com.ssg.item.dto.user;

import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserResDto {
    private long id;
    private String name;
    private UserType userType;
    private UserStat userStat;

    public UserResDto(long id, String name, UserType userType, UserStat userStat) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.userStat = userStat;
    }
}
