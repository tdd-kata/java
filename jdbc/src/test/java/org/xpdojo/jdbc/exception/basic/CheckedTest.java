package org.xpdojo.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class CheckedTest {

    @Test
    @DisplayName("Checked exception 을 던진다")
    void throws_checked_exception() {
        Controller controller = new Controller();
        assertThatThrownBy(controller::connect)
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Checked exception 을 catch로 처리한다")
    void catch_checked_exception() {
        Controller controller = new Controller();
        assertThatThrownBy(controller::query)
                .isInstanceOf(RuntimeException.class);
    }

    static class Controller {
        Service service = new Service();

        public void connect() throws ConnectException {
            service.connect();
        }

        public void query() {
            try {
                service.query();
            } catch (SQLException e) {
                log.error("예외 처리, message={}", e.getLocalizedMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Checked exception은 throws 키워드를 통해 명시하거나 catch 해야 한다.
     */
    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void connect() throws ConnectException {
            networkClient.connect();
        }

        public void query() throws SQLException {
            repository.query();
        }
    }

    static class NetworkClient {
        public void connect() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {
        public void query() throws SQLException {
            throw new SQLException("exception");
        }
    }
}
