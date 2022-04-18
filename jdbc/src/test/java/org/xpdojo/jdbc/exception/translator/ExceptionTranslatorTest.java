package org.xpdojo.jdbc.exception.translator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.api.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.xpdojo.jdbc.connection.ConnectionConst;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.exception.DuplicatePrimaryKeyException;
import org.xpdojo.jdbc.repository.exception.SQLRuntimeException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ExceptionTranslatorTest {

    Repository repository;
    Service service;

    @BeforeEach
    void init() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource(
                        ConnectionConst.URL,
                        ConnectionConst.USERNAME,
                        ConnectionConst.PASSWORD
                );

        repository = new Repository(dataSource);
        service = new Service(repository);
    }

    @Test
    @DisplayName("23505는 H2 database에서만 중복 키 에러 코드다")
    void duplicateKeySave() {
        // given
        Member member1 = service.create("sameId");
        Member member2 = service.create("sameId");// 같은 ID 저장 시도

        // when
        assertThat(member1.getId())
                .isNotEqualTo(member2.getId());

        // tear down
        repository.delete(member1.getId());
        repository.delete(member2.getId());
    }

    @Slf4j
    @RequiredArgsConstructor
    static class Service {
        private final Repository repository;

        public Member create(String memberId) {
            try {
                log.info("saveId={}", memberId);
                return repository.save(new Member(memberId, 0));
            } catch (DuplicatePrimaryKeyException e) {
                log.info("키 중복, 복구 시도");
                String retryId = generateNewId(memberId);
                log.info("retryId={}", retryId);
                return repository.save(new Member(retryId, 0));
            } catch (SQLRuntimeException e) {
                log.info("데이터 접근 계층 예외", e);
                throw e;
            }
        }

        private String generateNewId(String memberId) {
            return memberId + new Random().nextInt(10_000);
        }

    }

    @RequiredArgsConstructor
    static class Repository {
        private final DataSource dataSource;

        public Member save(Member member) {
            String sql = "insert into member(member_id, money) values(?,?)";
            Connection connection = null;
            PreparedStatement statement = null;

            try {
                connection = dataSource.getConnection();
                statement = connection.prepareStatement(sql);
                statement.setString(1, member.getId());
                statement.setInt(2, member.getMoney());
                statement.executeUpdate();
                return member;
            } catch (SQLException e) {
                // H2 Database 만 해당되는 Error Code
                if (e.getErrorCode() == ErrorCode.DUPLICATE_KEY_1) {
                    throw new DuplicatePrimaryKeyException(e);
                }
                throw new SQLRuntimeException(e);
            } finally {
                JdbcUtils.closeStatement(statement);
                JdbcUtils.closeConnection(connection);
            }
        }

        /**
         * 테스트 정리
         *
         * @param memberId 삭제할 사용자 ID
         */
        public void delete(String memberId) {

            String sql = "delete from member where member_id = ?";
            Connection connection = null;
            PreparedStatement pstmt = null;

            try {
                connection = dataSource.getConnection();
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, memberId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new SQLRuntimeException(e);
            } finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(connection);
            }
        }
    }
}
