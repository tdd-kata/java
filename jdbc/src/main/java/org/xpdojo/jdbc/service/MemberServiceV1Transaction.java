package org.xpdojo.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV2WithTransaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Service
public class MemberServiceV1Transaction {

    private final DataSource dataSource;
    private final MemberRepositoryV2WithTransaction memberRepository;

    public MemberServiceV1Transaction(DataSource dataSource, MemberRepositoryV2WithTransaction memberRepository) {
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        Connection connection = dataSource.getConnection();

        try {
            // 트랜잭션 시작
            connection.setAutoCommit(false);

            // 비즈니스 로직 수행
            logic(connection, fromId, toId, amount);

            // 트랜잭션 성공 시 커밋
            connection.commit();
        } catch (Exception e) {
            // 트랜잭션 실패 시 롤백
            connection.rollback();
            throw e;
        } finally {
            // 커넥션 종료
            release(connection);
        }

    }

    /**
     * Connection이 추가되었다.
     *
     * @param connection DB 커넥션
     * @param fromId     출금 회원 아이디
     * @param toId       입금 회원 아이디
     * @param amount     입금 금액
     */
    private void logic(Connection connection, String fromId, String toId, int amount) throws SQLException {
        Member fromMember = memberRepository.findById(connection, fromId);
        Member toMember = memberRepository.findById(connection, toId);

        memberRepository.update(connection, fromId, fromMember.getMoney() - amount);
        validate(toMember);
        memberRepository.update(connection, toId, toMember.getMoney() + amount);
    }

    private void validate(Member toMember) {
        String testMemberId = "ex";
        if (testMemberId.equals(toMember.getId())) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }

    private void release(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true); // 커넥션 풀 고려
                connection.close();
            } catch (SQLException e) {
                log.error("커넥션 닫기 실패", e);
            }
        }
    }
}
