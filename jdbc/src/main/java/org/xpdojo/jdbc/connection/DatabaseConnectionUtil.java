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
        log.info("Connection established=[{}], class=[{}]", connection, connection.getClass());
        return connection;
    }

    public static void closeConnection(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("Error closing statement", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Error closing statement", e);
            }
        }
        log.info("Closing connection=[{}]", connection);
    }
}
