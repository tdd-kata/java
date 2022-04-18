package org.xpdojo.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV3WithTransactionManager;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV2TransactionManager {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3WithTransactionManager memberRepository;

    public MemberServiceV2TransactionManager(PlatformTransactionManager transactionManager, MemberRepositoryV3WithTransactionManager memberRepository) {
        this.transactionManager = transactionManager;
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // 비즈니스 로직 수행
            logic(fromId, toId, amount);

            // 트랜잭션 성공 시 커밋
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 트랜잭션 실패 시 롤백
            transactionManager.rollback(transactionStatus);
            throw e;
        } finally {
            // 커넥션 종료는 자동으로 수행된다.
        }

    }

    /**
     * from에서 to로 이체
     *
     * @param fromId 출금 회원 아이디
     * @param toId   입금 회원 아이디
     * @param amount 입금 금액
     */
    private void logic(String fromId, String toId, int amount) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - amount);
        validate(toMember);
        memberRepository.update(toId, toMember.getMoney() + amount);
    }

    private void validate(Member toMember) {
        String testMemberId = "ex";
        if (testMemberId.equals(toMember.getId())) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
