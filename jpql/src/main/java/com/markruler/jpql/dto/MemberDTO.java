package com.markruler.jpql.dto;

public class MemberDTO {

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    private String username;
    private int age;

    @Override
    public String toString() {
        return "MemberDTO{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
