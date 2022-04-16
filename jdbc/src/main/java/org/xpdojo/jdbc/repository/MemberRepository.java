package org.xpdojo.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.xpdojo.jdbc.connection.DatabaseConnectionUtil;
import org.xpdojo.jdbc.domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC - Driver Manager
 */
@Slf4j
public class MemberRepository {

    public Member save(Member member) throws SQLException {
        String sql = "INSERT INTO member (member_id, money) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnectionUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error(e.toString(), e);
            throw e;
        } finally {
            DatabaseConnectionUtil.closeConnection(connection, preparedStatement);
        }
    }
}
