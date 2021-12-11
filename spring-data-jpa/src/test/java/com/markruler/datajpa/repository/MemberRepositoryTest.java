package com.markruler.datajpa.repository;

import com.markruler.datajpa.dto.MemberDto;
import com.markruler.datajpa.entity.Member;
import com.markruler.datajpa.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
@DisplayName("Spring Data JPA")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

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

        assertThat(members.get(0).getUsername()).isEqualTo(member2.getUsername());
        assertThat(members.get(0).getAge()).isEqualTo(member2.getAge());
    }

    @Test
    @DisplayName("미리 작성한 NamedQuery로 쿼리를 실행할 수 있다")
    void named_query() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findByUsername(member2.getUsername());

        assertThat(members.get(0).getUsername()).isEqualTo(member2.getUsername());
        assertThat(members.get(0).getAge()).isEqualTo(member2.getAge());
    }

    @Test
    @DisplayName("Query Annotation을 사용해 직접 쿼리를 작성해서 실행할 수 있다")
    void query_annotation() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        /*
            select
                member0_.member_id as member_i1_0_,
                member0_.age as age2_0_,
                member0_.team_id as team_id4_0_,
                member0_.username as username3_0_
            from
                member member0_
            where
                (
                    member0_.username in (
                        ? , ?
                    )
                )
                and member0_.age=?
         */
        List<Member> members = memberRepository.findUser(Arrays.asList(member1.getUsername(), member2.getUsername()), member1.getAge());

        assertThat(members.get(0).getUsername()).isEqualTo(member1.getUsername());
        assertThat(members.get(0).getAge()).isEqualTo(member1.getAge());
    }

    @Test
    @DisplayName("Query Annotation을 사용해 DTO를 조회할 수 있다")
    void query_annotation_find_dto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member = new Member("member1", 10);
        member.setTeam(team);
        memberRepository.save(member);

        /*
            select
                member0_.member_id as col_0_0_,
                member0_.username as col_1_0_,
                team1_.name as col_2_0_
            from
                member member0_
            inner join
                team team1_
                    on member0_.team_id=team1_.team_id
         */
        List<MemberDto> members = memberRepository.findMemberDto();

        assertThat(members.get(0).getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    @DisplayName("query creation을 사용해 Spring Data JPA로 페이징 기능을 활용할 수 있다")
    void paging() {

        // given
        final int age = 10;
        memberRepository.save(new Member("member1", age));
        memberRepository.save(new Member("member2", age));
        memberRepository.save(new Member("member3", age));
        memberRepository.save(new Member("member4", age));
        memberRepository.save(new Member("member5", age));

        final int page = 0; // offset이 들어가지 않고 countQuery를 실행한다.
        // final int page = 1; // offset이 포함되며 countQuery를 별도로 실행하지 않는다.
        final int size = 3;
        final Sort sort = Sort.by("username").descending();
        final PageRequest pageRequest = PageRequest.of(page, size, sort);

        // when
        /*
            select
                member0_.member_id as member_i1_0_,
                member0_.age as age2_0_,
                member0_.team_id as team_id4_0_,
                member0_.username as username3_0_
            from
                member member0_
            where
                member0_.age=?
            order by
                member0_.username desc limit ?
         */
        // countQuery를 별도로 선언하지 않으며 아래처럼 count 쿼리에도 join이 들어간다.
        /*
            select
                count(member0_.member_id) as col_0_0_
            from
                member member0_
            left outer join
                team team1_
                    on member0_.team_id=team1_.team_id
         */
        // countQuery 선언 시
        /*
            select
                count(member0_.member_id) as col_0_0_
            from
                member member0_
         */
        Page<Member> pageMember = memberRepository.findByAge(age, pageRequest);

        // then
        List<Member> contentMember = pageMember.getContent();

        assertThat(pageMember).hasSize(3);
        assertThat(pageMember.getSize()).isEqualTo(3);
        assertThat(contentMember).hasSize(3);
        assertThat(contentMember.size()).isEqualTo(3);
        assertThat(pageMember.getTotalPages()).isEqualTo(2);
        assertThat(pageMember.getTotalElements()).isEqualTo(5);
        assertThat(pageMember.getNumber()).isZero();
        assertThat(pageMember.getTotalPages()).isEqualTo(2);
        assertThat(pageMember.isFirst()).isTrue(); // !hasPrevious()
        assertThat(pageMember.hasPrevious()).isFalse(); // getNumber() > 0
        assertThat(pageMember.hasNext()).isTrue(); // getNumber() + 1 < getTotalPages()
    }
}
