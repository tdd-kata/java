package com.markruler.datajpa.controller;

import com.markruler.datajpa.dto.MemberDto;
import com.markruler.datajpa.entity.Member;
import com.markruler.datajpa.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    // page index가 0부터 시작한다는 걸 감안하고 사용해야 한다.
    @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 5) @Qualifier("member") Pageable pageable) {
        // @PageableDefault은 application.yaml의 global 설정보다 우선한다.
        // @Qualifier는 받는 객체가 여러개일 때 유용하다.
        // http GET localhost:8080/members member_page==1 member_size==2 member_sort==id,desc
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members/dto")
    public Page<MemberDto> listDto(@PageableDefault(size = 5) Pageable pageable) {
        // http GET localhost:8080/members/dto page==1 size==3 sort==id,desc
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

    // DI 후 초기화
    // @javax.annotation.PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }

}
