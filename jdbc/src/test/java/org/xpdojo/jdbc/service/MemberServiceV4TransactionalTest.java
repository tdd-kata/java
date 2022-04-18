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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.xpdojo.jdbc.connection.ConnectionConst;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV3WithTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 스프링의 트랜잭션 추상화
 *
 * @see org.springframework.transaction.annotation.Transactional
 */
@Slf4j
@DisplayName("Transactional을 사용해서 Transaction을 유지한다")
@SpringBootTest
class MemberServiceV4TransactionalTest {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3WithTransactionManager memberRepository;
    @Autowired
    private MemberServiceV4Transactional memberService;

    @TestConfiguration
    static class TestAppConfig {
        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(
                    ConnectionConst.URL,
                    ConnectionConst.USERNAME,
                    ConnectionConst.PASSWORD
            );
        }

        @Bean
        PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepositoryV3WithTransactionManager memberRepository() {
            return new MemberRepositoryV3WithTransactionManager(dataSource());
        }

        @Bean
        MemberServiceV4Transactional memberService() {
            return new MemberServiceV4Transactional(memberRepository());
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
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
    void sut_account_transfer() throws SQLException {
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
    void sut_account_transfer_with_exception() throws SQLException {
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
}
