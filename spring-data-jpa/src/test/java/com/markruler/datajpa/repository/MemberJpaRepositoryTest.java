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

/*
    Settings > Build, Execution, Deployment > Build Tools > Gradle > Build and run using: IntelliJ IDEA, Run tests using: IntelliJ IDEA
 */
@Transactional
/*
    @Transactional 없으면 예외 발생
    org.springframework.dao.InvalidDataAccessApiUsageException:
    No EntityManager with actual transaction available for current thread
    - cannot reliably process 'persist' call; nested exception is javax.persistence.TransactionRequiredException:
*/
@SpringBootTest
@Rollback(false) // 실무에선 DB에 데이터가 남기 때문에 사용하지 않는다.
@DisplayName("Basic JPA")
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("Repository 프록시 객체")
    void testRepository() {
        final Class<MemberJpaRepository> originalClass = MemberJpaRepository.class;
        final Class<? extends MemberJpaRepository> proxyClass = memberJpaRepository.getClass();
        final String cglibProxyPostfix = "$$EnhancerBySpringCGLIB$$";

        System.out.println(originalClass); // class com.markruler.datajpa.repository.MemberJpaRepository
        System.out.println(proxyClass); // class com.markruler.datajpa.repository.MemberJpaRepository$$EnhancerBySpringCGLIB$$a14d144

        assertThat(proxyClass.getSimpleName())
                .contains(originalClass.getSimpleName() + cglibProxyPostfix);
    }

    @Test
    @DisplayName("MemberJpaRepository")
    void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    @DisplayName("기본 CRD")
    void basicCRD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // 리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all).hasSize(2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        // 카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isZero();

        // Update는 어떻게 테스트하지
    }

    @Test
    @DisplayName("JPA를 그대로 사용한다면 쿼리를 직접 작성해야 한다")
    void not_query_creation() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> members = memberJpaRepository.findByUsernameAndAgeGreaterThan("member2", 15);

        assertThat(members.get(0).getUsername()).isEqualTo("member2");
        assertThat(members.get(0).getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("미리 작성한 NamedQuery로 쿼리를 실행할 수 있다")
    void named_query() {
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        List<Member> members = memberJpaRepository.findByUsername("member2");

        assertThat(members.get(0).getUsername()).isEqualTo("member2");
        assertThat(members.get(0).getAge()).isEqualTo(20);
    }

}
