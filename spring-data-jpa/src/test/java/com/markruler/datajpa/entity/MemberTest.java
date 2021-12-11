package com.markruler.datajpa.entity;

import com.markruler.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Member 엔터티 객체를 검증한다")
    void test_member_entity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();

        // 확인
        String query = "select m from Member m";
        List<Member> members = em
                .createQuery(query, Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }
    }

    @Test
    @DisplayName("엔터티 객체를 Auditing(감사)한다")
    void auditing() throws InterruptedException {
        /*
            등록일, 등록자
            수정일, 수정자
            위 데이터들은 데이터 변경을 추적하기 위해 모든 테이블에 있어야 한다.
         */
        /*
            // MappedSuperclass를 선언하기 전
            create table member (
               member_id bigint not null,
                age integer not null,
                username varchar(255),
                team_id bigint,
                primary key (member_id)
            )
         */
        /*
            // MappedSuperclass를 선언한 후
            create table member (
               member_id bigint not null,
                created_at timestamp,
                updated_at timestamp,
                age integer not null,
                username varchar(255),
                team_id bigint,
                primary key (member_id)
            )
         */
        // given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println("member.createdAt = " + findMember.getCreatedAt());
        System.out.println("member.updatedAt = " + findMember.getUpdatedAt());
        assertThat(findMember.getCreatedAt()).isExactlyInstanceOf(LocalDateTime.class);
        assertThat(findMember.getUpdatedAt()).isExactlyInstanceOf(LocalDateTime.class);
        assertThat(findMember.getCreatedBy()).isExactlyInstanceOf(String.class);
        assertThat(findMember.getCreatedBy()).isNotEmpty();
        assertThat(findMember.getUpdatedBy()).isExactlyInstanceOf(String.class);
        assertThat(findMember.getUpdatedBy()).isNotEmpty();
    }
}
