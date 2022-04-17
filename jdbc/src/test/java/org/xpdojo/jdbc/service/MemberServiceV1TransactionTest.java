package org.xpdojo.jdbc.service;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xpdojo.jdbc.connection.ConnectionConst;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV2WithTransaction;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
// @TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll, @AfterAll 사용할 경우 필요
@DisplayName("Connection을 전달해서 Transaction을 유지한다")
class MemberServiceV1TransactionTest {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV2WithTransaction memberRepository;
    private MemberServiceV1Transaction memberService;

    @BeforeEach
    void setUp() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setDriverClassName(ConnectionConst.DRIVER_CLASS_NAME); // 지정하지 않아도 라이브러리에서 자동으로 찾음
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyConnectionPool");
        dataSource.setConnectionTimeout(1000);
        dataSource.setIdleTimeout(1000);
        // dataSource.setAutoCommit(false);
        dataSource.setConnectionTestQuery("SELECT 1");

        memberRepository = new MemberRepositoryV2WithTransaction(dataSource);
        memberService = new MemberServiceV1Transaction(dataSource, memberRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
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
