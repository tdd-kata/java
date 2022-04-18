package org.xpdojo.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.xpdojo.jdbc.domain.Member;

import javax.sql.DataSource;

/**
 * {@link JdbcTemplate}을 사용해서 중복 코드를 제거한다.
 *
 * @see org.springframework.jdbc.core.JdbcTemplate
 */
@Slf4j
public class MemberRepositoryV7JdbcTemplate implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryV7JdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 사용자를 DB에 저장한다.
     *
     * @param member 사용자
     * @return 저장된 사용자 수
     */
    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";
        int updatedRowCount = jdbcTemplate.update(sql, member.getId(), member.getMoney());
        log.info("inserted row count = {}", updatedRowCount);
        return member;
    }

    /**
     * 사용자를 DB에서 조회한다.
     *
     * @param memberId 사용자 ID
     * @return 사용자
     */
    @Override
    public Member findById(String memberId) {
        String sql = "SELECT member_id, money FROM member WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId);
    }

    /**
     * 사용자를 DB에서 수정한다.
     *
     * @param memberId 사용자 ID
     * @param money    사용자의 돈
     * @return 수정된 사용자 수
     */
    public int update(String memberId, int money) {
        String sql = "UPDATE member SET money = ? WHERE member_id = ?";
        int updatedRowCount = jdbcTemplate.update(sql, money, memberId);
        log.info("updated row count = {}", updatedRowCount);
        return updatedRowCount;
    }

    /**
     * 사용자를 DB에서 삭제한다.
     *
     * @param memberId 사용자 ID
     * @return 삭제된 사용자 수
     */
    @Override
    public int delete(String memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";
        int deletedRowCount = jdbcTemplate.update(sql, memberId);
        log.info("deleted row count = {}", deletedRowCount);
        return deletedRowCount;
    }

    /**
     * ResultSet을 Member로 변환한다.
     *
     * @return 사용자
     */
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) ->
                Member.builder()
                        .id(rs.getString("member_id"))
                        .money(rs.getInt("money"))
                        .build();
    }
}
