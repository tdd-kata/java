package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA에서 제공해주는 인터페이스
 *
 * @see org.springframework.data.repository.Repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
