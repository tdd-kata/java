package org.xpdojo.jdbc.repository;

import org.xpdojo.jdbc.domain.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findById(String memberId);

    int update(String memberId, int money);

    int delete(String memberId);

}
