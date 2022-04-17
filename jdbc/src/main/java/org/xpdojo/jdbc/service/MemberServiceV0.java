package org.xpdojo.jdbc.service;

import org.springframework.stereotype.Service;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.MemberRepositoryV1WithDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class MemberServiceV0 {

    private final DataSource dataSource;
    private final MemberRepositoryV1WithDataSource memberRepository;

    public MemberServiceV0(DataSource dataSource, MemberRepositoryV1WithDataSource memberRepository) {
        this.dataSource = dataSource;
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false); // 자동 커밋 비활성화

        try {
            logic(fromId, toId, amount);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.close();
            throw e;
        }

    }

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
