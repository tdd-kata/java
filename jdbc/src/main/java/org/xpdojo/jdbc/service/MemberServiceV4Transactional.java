package org.xpdojo.jdbc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV3WithTransactionManager;

import java.sql.SQLException;

/**
 * <code>@Transactional</code> 애노테이션을 사용해서 비즈니스 로직과 트랜잭션 처리 로직을 분리할 수 있다.
 *
 * @see org.springframework.transaction.annotation.Transactional
 */
@Slf4j
public class MemberServiceV4Transactional {

    private final MemberRepositoryV3WithTransactionManager memberRepository;

    public MemberServiceV4Transactional(MemberRepositoryV3WithTransactionManager memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 테스트 시 주석 처리해보자
    @Transactional
    public void accountTransfer(String fromId, String toId, int amount)
            throws SQLException {
        logic(fromId, toId, amount);
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
