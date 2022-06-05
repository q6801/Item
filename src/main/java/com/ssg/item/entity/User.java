package com.ssg.item.entity;


import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
