package org.xpdojo.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepository;
import org.xpdojo.jdbc.repository.MemberRepositoryV6WithExceptionTranslator;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 설정 파일에 DataSource 설정 정보를 입력하면
 * DataSource와 TransactionManager를 따로 생성할 필요없다.
 *
 * @see DataSource
 * @see org.springframework.transaction.PlatformTransactionManager
 * @see org.springframework.transaction.annotation.Transactional
 */
@Slf4j
@DisplayName("Transactional을 사용해서 Transaction을 유지한다")
@SpringBootTest
class MemberServiceV5RuntimeExceptionTest {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberServiceV5RuntimeException memberService;

    @TestConfiguration
    static class TestAppConfig {

        /**
         * 트랜잭션 처리를 위한 DataSource를 생성한다.
         * 설정 파일(application.yaml)에 별도로 설정하지 않으면 인메모리 DB를 사용한다.
         * <p>
         * conn0: url=jdbc:h2:tcp://localhost:9092/test user=SA class=class com.zaxxer.hikari.pool.HikariProxyConnection
         */
        private final DataSource dataSource;

        TestAppConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository() {
            // return new MemberRepositoryV5WithRuntimeException(dataSource);
            return new MemberRepositoryV6WithExceptionTranslator(dataSource);
        }

        @Bean
        MemberServiceV5RuntimeException memberService() {
            return new MemberServiceV5RuntimeException(memberRepository());
        }
    }

    @AfterEach
    void tearDown() {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("Proxy 객체 확인")
    void sut_proxy_bean() {
        // @Transactional 처리를 위한 Proxy
        // org.xpdojo.jdbc.service.MemberServiceV4Transactional$$EnhancerBySpringCGLIB$$cf947dc
        assertThat(memberService).isNotNull();
        log.info("memberService class: {}", memberService.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();

        // org.xpdojo.jdbc.repository.MemberRepositoryV3WithTransactionManager
        assertThat(memberRepository).isNotNull();
        log.info("memberRepository class: {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }

    @Test
    @DisplayName("정상 이체")
    void sut_account_transfer() {
        // given
        Member memberA = new Member(MEMBER_A, 10_000);
        memberRepository.save(memberA);
        Member memberB = new Member(MEMBER_B, 10_000);
        memberRepository.save(memberB);

        // when
        memberService.accountTransfer(memberA.getId(), memberB.getId(), 2_000);

        // then
        Member findMemberA = memberRepository.findById(memberA.getId());
        Member findMemberB = memberRepository.findById(memberB.getId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체 중 예외 발생")
    void sut_account_transfer_with_exception() {
        // given
        Member memberA = new Member(MEMBER_A, 10_000);
        memberRepository.save(memberA);
        Member memberException = new Member(MEMBER_EX, 10_000);
        memberRepository.save(memberException);

        // when
        assertThatThrownBy(() -> memberService.accountTransfer(memberA.getId(), memberException.getId(), 2_000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member findMemberA = memberRepository.findById(memberA.getId());
        Member findMemberException = memberRepository.findById(memberException.getId());
        assertThat(findMemberA.getMoney()).isEqualTo(10_000); // 정상적으로 rollback 된다.
        assertThat(findMemberException.getMoney()).isEqualTo(10_000);
    }

    @Test
    @DisplayName("ExceptionHandler를 사용해서 예외 추상화")
    void sut_exception_translator() {
        // given
        Member memberA1 = new Member(MEMBER_A, 10_000);
        Member memberA2 = new Member(MEMBER_A, 10_000);

        // when
        memberRepository.save(memberA1);
        assertThatThrownBy(() -> memberRepository.save(memberA2))
                .isInstanceOf(DuplicateKeyException.class);

        // tear down
        memberRepository.delete(memberA1.getId());
    }
}
