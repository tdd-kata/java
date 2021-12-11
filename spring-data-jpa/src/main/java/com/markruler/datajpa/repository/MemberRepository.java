package com.markruler.datajpa.repository;

import com.markruler.datajpa.dto.MemberDto;
import com.markruler.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
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

    // @Query는 이름이 없는 NamedQuery처럼 동작한다고 생각할 수 있다.
    // 컴파일 시 쿼리에 오류가 있다면 QueryException를 던질 수 있다.
    // 동적 쿼리는 QueryDSL을 사용한다.
    @Query("select m from Member m where m.username in :usernames and m.age = :age")
    List<Member> findUser(
            @Param("usernames") Collection<String> usernames, // Collection Parameter Binding
            @Param("age") int age
    );

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types
    @Query("select new com.markruler.datajpa.dto.MemberDto(m.id, m.username, t.name)" +
            " from Member m join m.team t")
    List<MemberDto> findMemberDto();
}
