package com.markruler.querydsl.repository;

import com.markruler.querydsl.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("순수 JPA Repository와 JPQL")
    void sut_jpql() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> members1 = memberJpaRepository.findAll();
        assertThat(members1).containsExactly(member);

        List<Member> members2 = memberJpaRepository.findByUsername(member.getUsername());
        assertThat(members2).containsExactly(member);
    }

    @Test
    @DisplayName("순수 JPA Repository와 Querydsl")
    void sut_querydsl() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> members1 = memberJpaRepository.findAll_Querydsl();
        assertThat(members1).containsExactly(member);

        List<Member> members2 = memberJpaRepository.findByUsername_Querydsl(member.getUsername());
        assertThat(members2).containsExactly(member);
    }
}
