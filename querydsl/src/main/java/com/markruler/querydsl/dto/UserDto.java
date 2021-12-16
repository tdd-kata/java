package com.markruler.querydsl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private String username;
    private int age;

    public UserDto() {
    }

    public UserDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
