package com.ssg.item.enums;

public enum UserStat {
    NORMAL("정상"),
    WITHDRAWAL("탈퇴");

    private final String stat;

    UserStat(String stat) {
        this.stat = stat;
    }
}
