package com.markruler.datajpa.controller;

import com.markruler.datajpa.entity.Member;
import com.markruler.datajpa.repository.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    /**
     * Domain Class Converter 활용
     * 실무에서는 컨트롤러에서 엔터티를 주고 받지 말자
     *
     * @param member 조회하려는 사용자
     * @return 사용자 이름
     * @see org.springframework.data.repository.support.DomainClassConverter
     */
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) { // Domain Class Converter
        return member.getUsername();
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }

}
