package com.markruler.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberProjectionDto {

    private String username;
    private int age;

    // 기본 생성자 필요 없음
    @QueryProjection
    public MemberProjectionDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
