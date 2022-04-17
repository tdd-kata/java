package org.xpdojo.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * DataSource의 핵심 가치는 설정과 사용의 분리다.
 * 이렇게 설정과 관련된 속성들을 한 곳에 모으면 변경에 더 유연하게 대처할 수 있다.
 *
 * @see java.sql.Connection
 * @see java.sql.DriverManager#getConnection(String, String, String)
 * @see javax.sql.DataSource
 * @see org.springframework.jdbc.datasource.DriverManagerDataSource
 */
@Slf4j
class DataSourceConnectionTest {

    @Nested
    @DisplayName("커넥션을 얻기 위해 getConnection() 호출 시")
    class Describe_getConnection {

        /**
         * @see java.sql.Connection
         * @see java.sql.DriverManager#getConnection(String, String, String)
         */
        @Nested
        @DisplayName("DriverManager를 사용할 경우")
        class Context_with_driver_manager {

            @Test
            @DisplayName("DB 연결 정보가 필요하다")
            void sut_driver_manager() throws SQLException {
                Connection connection1 = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
                log.info("connection=[{}], class=[{}]", connection1, connection1.getClass());
                Connection connection2 = DriverManager.getConnection(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
                log.info("connection=[{}], class=[{}]", connection2, connection2.getClass());

                assertThat(connection1).isNotEqualTo(connection2);
            }
        }

        /**
         * @see javax.sql.DataSource
         * @see org.springframework.jdbc.datasource.DriverManagerDataSource
         */
        @Nested
        @DisplayName("DataSource를 사용할 경우")
        class Context_with_dataSource {

            @Test
            @DisplayName("DB 연결 정보가 필요없다")
            void sut_driver_manager_datasource() throws SQLException {
                DataSource dataSource = new DriverManagerDataSource(ConnectionConst.URL, ConnectionConst.USERNAME, ConnectionConst.PASSWORD);
                Connection connection1 = dataSource.getConnection();
                log.info("connection=[{}], class=[{}]", connection1, connection1.getClass());
                Connection connection2 = dataSource.getConnection();
                log.info("connection=[{}], class=[{}]", connection2, connection2.getClass());

                assertThat(connection1).isNotEqualTo(connection2);
            }
        }

        /**
         * @see com.zaxxer.hikari.HikariDataSource
         */
        @Nested
        @TestInstance(Lifecycle.PER_CLASS)
        @DisplayName("DataSource를 사용해서 Connection Pool에 담긴 커넥션을 얻는 경우")
        class Context_with_dataSource_and_connection_pool {
            private HikariDataSource dataSource;

            /**
             * <code>@BeforeAll</code> method must be static unless the test class is annotated with
             * <code>@TestInstance(Lifecycle.PER_CLASS)</code>.
             *
             * @throws InterruptedException
             */
            @BeforeAll
            void setDataSource() {
                dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(ConnectionConst.URL);
                dataSource.setDriverClassName(ConnectionConst.DRIVER_CLASS_NAME); // 지정하지 않아도 라이브러리에서 자동으로 찾음
                // 지정했을 경우
                // [main] DEBUG com.zaxxer.hikari.HikariConfig - driverClassName................................"org.h2.Driver"
                // 지정하지 않았을 경우
                // [main] DEBUG com.zaxxer.hikari.HikariConfig - driverClassName................................none
                dataSource.setUsername(ConnectionConst.USERNAME);
                dataSource.setPassword(ConnectionConst.PASSWORD);
                dataSource.setMaximumPoolSize(10);
                dataSource.setPoolName("MyConnectionPool");
                dataSource.setConnectionTimeout(1000);
                dataSource.setIdleTimeout(1000);
                dataSource.setAutoCommit(false);
                dataSource.setConnectionTestQuery("SELECT 1");
            }

            @Test
            @DisplayName("DB 연결 정보가 필요없다")
            void sut_driver_manager_datasource() throws SQLException, InterruptedException {

                // getConnection()을 해야 커넥션을 생성해서 커넥션 풀에 담는다.
                Connection connection1 = dataSource.getConnection();
                Connection connection2 = dataSource.getConnection();
                Connection connection3 = dataSource.getConnection();
                Connection connection4 = dataSource.getConnection();
                Connection connection5 = dataSource.getConnection();
                Connection connection6 = dataSource.getConnection();
                Connection connection7 = dataSource.getConnection();
                Connection connection8 = dataSource.getConnection();
                Connection connection9 = dataSource.getConnection();
                Connection connection10 = dataSource.getConnection();

                // Connection connection11 = dataSource.getConnection();
                // dataSource.setConnectionTimeout(1000);
                // MyConnectionPool - Connection is not available, request timed out after 1014ms.

                assertThat(connection1).isNotEqualTo(connection2);

                // Connection을 담는 로그를 확인하기 위해 일정 시간 대기
                Thread.sleep(1000);
                // [main] INFO com.zaxxer.hikari.HikariDataSource - MyConnectionPool - Starting...
                // [main] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn0: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [main] INFO com.zaxxer.hikari.HikariDataSource - MyConnectionPool - Start completed.
                // ...
                // 별도의 쓰레드를 사용해서 커넥션 풀에 커넥션을 담는다.
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn1: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool housekeeper] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Pool stats (total=2, active=2, idle=0, waiting=0)
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn2: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn3: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn4: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn5: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn6: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn7: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn8: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - Added connection conn9: url=jdbc:h2:tcp://localhost:9092/test user=SA
                // [MyConnectionPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool - MyConnectionPool - After adding stats (total=10, active=2, idle=8, waiting=0)
            }
        }
    }
}
