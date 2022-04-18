package org.xpdojo.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV3WithTransactionManager;

import java.sql.SQLException;

/**
 * @see TransactionTemplate
 */
@Slf4j
public class MemberServiceV3TransactionTemplate {

    private final TransactionTemplate transactionTemplate;
    private final MemberRepositoryV3WithTransactionManager memberRepository;

    public MemberServiceV3TransactionTemplate(PlatformTransactionManager transactionManager, MemberRepositoryV3WithTransactionManager memberRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) {
        transactionTemplate.executeWithoutResult(status -> {
            try {
                logic(fromId, toId, amount);
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        });
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
