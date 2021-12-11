package com.markruler.datajpa.repository;

import com.markruler.datajpa.dto.MemberDto;
import com.markruler.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA에서 제공해주는 인터페이스
 *
 * @see org.springframework.data.repository.Repository
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Query Creation
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // Named Query
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

    // various return types - DTO, VO, etc.
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-return-types
    @Query("select new com.markruler.datajpa.dto.MemberDto(m.id, m.username, t.name)" +
            " from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // Paging
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    // Bulk
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.modifying-queries
    // 기본적으로 벌크 연산은 영속성 컨텍스트를 거치지 않고 바로 DB에 저장한다.
    // 영속성 컨텍스트를 clear 하지 않으면 같은 엔터티를 다시 조회할 때 변경되지 않은 1차 캐시가 조회된다.
    // 그리고 혹시 flush 되지 않은 변경 사항이 있을 수 있으니 flush 한다.
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // Fetch Join
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // Entity Graph
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // @EntityGraph("Member.all")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

}
