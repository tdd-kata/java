package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;

import java.util.List;

// 핵심 비즈니스 로직을 담은 인터페이스와
// 단순히 외부와 연동하기 위한(예를 들어, 화면에 맞추기 위한) 인터페이스는 분리한다.
public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
