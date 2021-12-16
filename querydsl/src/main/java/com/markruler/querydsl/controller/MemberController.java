package com.markruler.querydsl.controller;

import com.markruler.querydsl.dto.MemberSearchCondition;
import com.markruler.querydsl.dto.MemberTeamDto;
import com.markruler.querydsl.repository.MemberJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;

    public MemberController(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(final MemberSearchCondition condition) {
        // http GET http://localhost:8080/v1/members?teamName=teamB&ageGoe=30&ageLoe=35
        return memberJpaRepository.search(condition);
    }
}
