package com.markruler.querydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.markruler.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
// @org.springframework.test.annotation.Rollback
// @org.springframework.test.annotation.Commit
class MemberTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        /*
            JPAQueryFactory의 동시성 문제는 EntityManger에 의존한다.
            스프링 프레임워크는 여러 쓰레드에서 동시에 같은 EntityManager에 접근해도,
            트랜잭션마다 별도의 영속성 컨텍스트를 제공하기 때문에,
            동시성 문제는 걱정하지 않아도 된다.
         */
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamA);
        Member member4 = new Member("member4", 40, teamA);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @AfterEach
    void cleanup() {
        em.close();
    }

    @Test
    @DisplayName("JPQL은 쿼리에 오타가 있을 경우 런타임에 오류가 발생한다")
    void sut_jpql() {

        String qlString =
                "select m from Member m " +
                        "where m.username = :username";

        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    @DisplayName("Querydsl은 쿼리에 오타가 있을 경우 컴파일 타임에 오류가 발생한다")
    void sut_querydsl() {
        // QMember m = QMember.member;
        // JPQL의 alias를 지정할 수 있다.
        // 같은 테이블을 조인할 경우에만 사용하면 된다.
        // 대부분은 `QMember.member`(static import)를 활용한다.
        // QMember m = new QMember("m");

        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Nested
    @DisplayName("검색 조건을")
    class Describe_search {

        @Test
        @DisplayName("메서드 체인 형태로 작성할 수 있다")
        void sut_search_with_method_chaining() {

            List<Member> findMember = queryFactory
                    .selectFrom(member)
                    .where(member.username.eq("member1")
                            // .and(member.age.eq(10)))
                            .and(member.age.between(10, 30)))
                    .fetch();

            assertThat(findMember).hasSize(1);
            assertThat(findMember.get(0).getUsername()).isEqualTo("member1");
        }

        @Test
        @DisplayName("매개 변수로 전달할 수 있다")
        void sut_search_with_params() {

            Member findMember = queryFactory
                    .selectFrom(member)
                    .where(
                            member.username.eq("member1"),
                            member.age.between(10, 30)
                    )
                    .fetchOne();

            assertThat(findMember).isNotNull();
            assertThat(findMember.getUsername()).isEqualTo("member1");
        }
    }
}
