package org.xpdojo.jdbc.connection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseConnectionUtilTest {

    @Test
    @DisplayName("Driver를 명시하지 않아도 JDBC는 라이브러리에 등록된 드라이버에 Test Connection을 수행한다")
    void testGetDatabaseConnection() throws SQLException {
        Connection connection = DatabaseConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
