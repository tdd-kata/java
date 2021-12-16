package com.markruler.querydsl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // for com.querydsl.core.types.Projections : 기본 생성자로 인스턴스 만든 후 setter 사용
public class MemberDto {

    private String username;
    private int age;

    // for com.querydsl.core.types.Projections을 사용하려면 기본 생성자가 필요하다.
    // `public` 이어야 한다.
    // class com.querydsl.core.types.QBean cannot access a member of class com.markruler.querydsl.dto.MemberDto with modifiers "protected"
    public MemberDto() {
    }

    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
