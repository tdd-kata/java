package com.markruler.querydsl.repository;

import com.markruler.querydsl.dto.MemberSearchCondition;
import com.markruler.querydsl.dto.MemberTeamDto;
import com.markruler.querydsl.entity.Member;
import com.markruler.querydsl.entity.Team;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        // em.flush();
        // em.clear();
    }

    @AfterEach
    void cleanup() {
        em.close();
    }

    @Nested
    @DisplayName("Spring Data JPA Repository")
    class Describe_jpa_repository {

        @Test
        @DisplayName("JPQL로 엔터티를 관리할 수 있다")
        void sut_jpql() {
            Member member = new Member("test", 99);
            memberRepository.save(member);

            Member findMember = memberRepository.findById(member.getId()).get();
            assertThat(findMember).isEqualTo(member);

            List<Member> members1 = memberRepository.findAll();
            assertThat(members1).contains(member);

            List<Member> members2 = memberRepository.findByUsername(member.getUsername());
            assertThat(members2).contains(member);
        }
    }

    @Nested
    @DisplayName("동적 쿼리를 생성할 수 있다")
    class Describe_complex_expressions {

        @Test
        @DisplayName("여러 개의 WHERE절 파라미터")
        void sut_complex_expressions_parameters() {
            MemberSearchCondition condition = new MemberSearchCondition();
            // condition.setUsername("member4");
            condition.setAgeGoe(35);
            condition.setAgeLoe(40);
            condition.setTeamName("teamB");

            List<MemberTeamDto> members = memberRepository.search(condition);

            assertThat(members)
                    .extracting("username")
                    .containsExactly("member4");
        }
    }

}
