package com.markruler.querydsl.controller;

import com.markruler.querydsl.dto.MemberSearchCondition;
import com.markruler.querydsl.dto.MemberTeamDto;
import com.markruler.querydsl.repository.MemberJpaRepository;
import com.markruler.querydsl.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    public MemberController(MemberJpaRepository memberJpaRepository, MemberRepository memberRepository) {
        this.memberJpaRepository = memberJpaRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(final MemberSearchCondition condition) {
        // http GET http://localhost:8080/v1/members?teamName=teamB&ageGoe=30&ageLoe=35
        return memberJpaRepository.search(condition);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(final MemberSearchCondition condition, Pageable pageable) {
        // http GET http://localhost:8080/v2/members?teamName=teamB&ageGoe=30&ageLoe=35
        return memberRepository.searchPageSimple(condition, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(final MemberSearchCondition condition, Pageable pageable) {
        // http GET http://localhost:8080/v3/members?teamName=teamB&ageGoe=30&ageLoe=35
        return memberRepository.searchPageComplex(condition, pageable);
    }
}
