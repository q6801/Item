package com.ssg.item.entity;


import com.ssg.item.dto.user.UserDto;
import com.ssg.item.dto.user.UserResDto;
import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name="user_name", length = 30)
    private String name;

    @Column(nullable = false, name="user_type", length = 30)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, name="user_stat", length = 30)
    @Enumerated(EnumType.STRING)
    private UserStat userStat;

    public User(long id, String name, UserType userType, UserStat userStat) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.userStat = userStat;
    }

    public User(String name, UserType userType, UserStat userStat) {
        this.name = name;
        this.userType = userType;
        this.userStat = userStat;
    }

    public static User convertDtoToUser(UserDto userDto) {
        return new User(userDto.getName(),
                userDto.getUserType(),
                userDto.getUserStat());
    }

    public UserResDto convertUserToResDto() {
        return new UserResDto(this.id, this.name, this.userType, this.userStat);
    }
}
