package com.ssg.item.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


@Getter
public enum UserStat {
    NORMAL("정상"),
    WITHDRAWAL("탈퇴");

    private final String stat;

    UserStat(String stat) {
        this.stat = stat;
    }

    @JsonCreator
    public static UserStat of(String stat) {
        return Arrays.stream(UserStat.values())
                .filter(u -> u.getStat().equals(stat)).findAny()
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.USER_STAT_NOT_FOUND));
    }

    @JsonValue
    public String getStat() {
        return stat;
    }
}
