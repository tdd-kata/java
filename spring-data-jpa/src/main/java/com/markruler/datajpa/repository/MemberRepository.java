package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA에서 제공해주는 인터페이스
 *
 * @see org.springframework.data.repository.Repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /*
        어노테이션을 작성하지 않아도
        Member 클래스의 findByUsername이라는 Named Query를 찾는다.
     */
    // @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

}
