package org.xpdojo.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class UncheckedTest {

    @Test
    @DisplayName("throws 키워드로 명시하지 않아도 RuntimeException 은 발생할 수 있다")
    void unchecked_exception() {
        Controller controller = new Controller();
        assertThatThrownBy(controller::connect)
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Checked Exception인 SQLException을 Unchecked Exception으로 변환한다")
    void translate_sql_exception_unchecked() {
        Controller controller = new Controller();
        assertThatThrownBy(controller::query)
                .isInstanceOf(RuntimeException.class);
    }

    static class Controller {
        Service service = new Service();

        public void query() {
            service.query();
        }

        public void connect() {
            service.connect();
        }
    }

    /**
     * UnChecked Exception은 잡거나 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void connect() {
            networkClient.call();
        }

        public void query() {
            repository.call();
        }

    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {

        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                // 필요한 경우 예외를 잡아서 처리하면 된다.
                log.error(e.toString(), e);
                throw new RuntimeSQLException(e);
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    /**
     * 네트워크 연결에 실패했을 경우 던지는 예외
     */
    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    /**
     * Checked Exception인 SQLException을 Unchecked Exception으로 변환한다.
     */
    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }


}
