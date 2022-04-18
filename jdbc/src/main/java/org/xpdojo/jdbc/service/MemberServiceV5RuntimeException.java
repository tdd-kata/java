package org.xpdojo.jdbc.service;

import org.springframework.transaction.annotation.Transactional;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepository;

/**
 * SQLException 을 제거하면서 외부 라이브러리(java.sql) 결합도를 낮춘다.
 *
 * @see org.xpdojo.jdbc.repository.MemberRepository
 */
public class MemberServiceV5RuntimeException {

    private final MemberRepository memberRepository;

    public MemberServiceV5RuntimeException(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 테스트 시 주석 처리해보자
    @Transactional
    public void accountTransfer(String fromId, String toId, int amount) {
        logic(fromId, toId, amount);
    }

    /**
     * from에서 to로 이체
     *
     * @param fromId 출금 회원 아이디
     * @param toId   입금 회원 아이디
     * @param amount 입금 금액
     */
    private void logic(String fromId, String toId, int amount) {
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
