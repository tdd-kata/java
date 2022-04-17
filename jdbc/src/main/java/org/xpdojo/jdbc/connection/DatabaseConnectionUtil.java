package org.xpdojo.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DatabaseConnectionUtil {

    private DatabaseConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                ConnectionConst.URL,
                ConnectionConst.USERNAME,
                ConnectionConst.PASSWORD);

        // 테스트 시 commit 하지 않음
        // rollback() 호출할 필요 없음
        // connection.setAutoCommit(false);

        log.info("Connection established=[{}], class=[{}]", connection, connection.getClass());
        return connection;
    }

    public static void closeConnection(Connection connection, Statement statement) {
        log.info("Closing connection=[{}]", connection);

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Error closing statement", e);
            }
        }
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                log.error("Error closing statement", e);
            }
        }
    }
}
