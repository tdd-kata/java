package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA
// @Repository 생략 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
}
