package org.xpdojo.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class DatabaseConnectionUtil {

    private DatabaseConnectionUtil() {
    }

    /**
     * DriverManager를 사용해서 Connection을 가져온다.
     *
     * @return Connection
     */
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

    /**
     * DataSource를 사용해서 ConnectionPool에서 Connection을 가져온다.
     *
     * @param dataSource DataSource
     * @return Connection
     */
    public static Connection getConnectionFromPool(DataSource dataSource) throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("Connection established=[{}], class=[{}]", connection, connection.getClass());
        return connection;
    }

    public static void closeConnectionByJdbcUtils(Connection connection, Statement statement, ResultSet resultSet) {
        JdbcUtils.closeConnection(connection);
        closedByJdbcUtils(statement, resultSet);
    }

    /**
     * Connection은 서비스 계층에서 열고 닫는다.
     *
     * @param statement
     * @param resultSet
     */
    public static void closedByJdbcUtils(Statement statement, ResultSet resultSet) {
        // JdbcUtils.closeConnection(connection);
        closedByJdbcUtils(statement);
        JdbcUtils.closeResultSet(resultSet);
    }

    public static void closedByJdbcUtils(Statement statement) {
        JdbcUtils.closeStatement(statement);
    }

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        closeConnection(connection, statement);

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("Error closing resultSet", e);
            }
        }
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
