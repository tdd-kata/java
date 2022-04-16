package org.xpdojo.jdbc.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xpdojo.jdbc.domain.Member;

import java.sql.SQLException;

class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();

    @Test
    @DisplayName("Member를 저장할 수 있다")
    void sut_save() throws SQLException {
        Member member = new Member("member1", 10_000);
        memberRepository.save(member);
    }

}
