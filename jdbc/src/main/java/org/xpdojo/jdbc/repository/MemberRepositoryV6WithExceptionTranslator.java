package org.xpdojo.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.xpdojo.jdbc.domain.Member;
import org.xpdojo.jdbc.repository.exception.SQLRuntimeException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

/**
 * <code>sql-error-codes.xml</code> 파일에 각 DBMS 에러 코드들을 매핑해놓았다.
 *
 * @see org.springframework.jdbc.support.sql-error-codes.xml
 * @see <a href="https://github.com/spring-projects/spring-framework/blob/v5.3.19/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml">sql-error-codes.xml</a>
 * @see org.springframework.jdbc.support.SQLExceptionTranslator
 * @see org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
 * @see org.springframework.jdbc.support.SQLExceptionSubclassTranslator
 * @see org.springframework.jdbc.support.SQLStateSQLExceptionTranslator
 */
@Slf4j
public class MemberRepositoryV6WithExceptionTranslator implements MemberRepository {

    private final DataSource dataSource;
    private final SQLExceptionTranslator exceptionTranslator;

    public MemberRepositoryV6WithExceptionTranslator(DataSource dataSource) {
        this.dataSource = dataSource;
        this.exceptionTranslator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

    /**
     * 사용자를 DB에 저장한다.
     *
     * @param member 사용자
     * @return 저장된 사용자 수
     */
    public Member save(Member member) {
        String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getId());
            preparedStatement.setInt(2, member.getMoney());
            int rowCount = preparedStatement.executeUpdate();
            log.info("saved row count = {}", rowCount);
            return member;
        } catch (SQLException e) {
            log.error(e.toString(), e);
            throw exceptionTranslator.translate("save 중 에러", sql, e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    /**
     * 사용자를 DB에서 조회한다.
     *
     * @param memberId 사용자 ID
     * @return 사용자
     * @see ResultSet#next()
     * @see org.h2.jdbc.JdbcResultSet#next()
     * @see com.zaxxer.hikari.pool.HikariProxyResultSet#next()
     */
    public Member findById(String memberId) {
        String sql = "select member_id, money from member where member_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Member.builder()
                        .id(resultSet.getString("member_id"))
                        .money(resultSet.getInt("money"))
                        .build();
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (SQLException e) {
            log.error(e.toString(), e);
            throw exceptionTranslator.translate("findById 중 에러", sql, e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    }

    public int update(String memberId, int money) {
        String sql = "UPDATE member SET money = ? WHERE member_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
            int rowCount = preparedStatement.executeUpdate();
            log.info("updated row count = {}", rowCount);
            return rowCount;
        } catch (SQLException e) {
            log.error(e.toString(), e);
            throw new SQLRuntimeException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    /**
     * 사용자를 DB에서 삭제한다.
     *
     * @param memberId 사용자 ID
     * @return 삭제된 사용자 수
     */
    public int delete(String memberId) {
        String sql = "DELETE FROM member WHERE member_id = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            int rowCount = preparedStatement.executeUpdate();
            log.info("deleted row count = {}", rowCount);
            return rowCount;
        } catch (SQLException e) {
            log.error(e.toString(), e);
            throw exceptionTranslator.translate("delete 중 에러", sql, e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        JdbcUtils.closeResultSet(resultSet);
        close(connection, statement);
    }

    private void close(Connection connection, Statement statement) {
        JdbcUtils.closeStatement(statement);
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    private Connection getConnection() throws SQLException {
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        Connection connection = DataSourceUtils.getConnection(dataSource);
        log.info("get connection={} class={}", connection, connection.getClass());
        return connection;
    }
}
