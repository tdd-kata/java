package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@DisplayName("Spring Data JPA")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Repository 프록시 객체")
    void test_repository() {
        final Class<MemberRepository> originalClass = MemberRepository.class;
        final Class<? extends MemberRepository> proxyClass = memberRepository.getClass();
        final String proxyName = "com.sun.proxy.$Proxy";

        System.out.println(originalClass); // interface com.markruler.datajpa.repository.MemberRepository
        System.out.println(proxyClass); // class com.sun.proxy.$Proxy117

        assertThat(proxyClass.getName())
                .contains(proxyName);
    }

    @Test
    @DisplayName("MemberRepository")
    void test_member() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("기본 CRD")
    void basic_create_read_delete() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all).hasSize(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isZero();

        // Update는 어떻게 테스트하지
    }

    @Test
    @DisplayName("org.springframework.data.repository.Repository를 구현한 객체는 메서드명으로 쿼리를 생성한다")
    void query_creation_by_method_name() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
        List<Member> members = memberRepository.findByUsernameAndAgeGreaterThan("member2", 15);

        assertThat(members.get(0).getUsername()).isEqualTo("member2");
        assertThat(members.get(0).getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("미리 작성한 NamedQuery로 쿼리를 실행할 수 있다")
    void named_query() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUsername("member2");

        assertThat(members.get(0).getUsername()).isEqualTo("member2");
        assertThat(members.get(0).getAge()).isEqualTo(20);
    }
}
