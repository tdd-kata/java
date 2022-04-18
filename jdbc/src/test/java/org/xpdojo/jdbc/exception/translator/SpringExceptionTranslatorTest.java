package org.xpdojo.jdbc.exception.translator;

import lombok.extern.slf4j.Slf4j;
import org.h2.api.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.xpdojo.jdbc.connection.ConnectionConst;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SpringExceptionTranslatorTest {

    private DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource(
                ConnectionConst.URL,
                ConnectionConst.USERNAME,
                ConnectionConst.PASSWORD
        );
    }

    @Test
    @DisplayName("SQL 구문이 잘못된 경우 42122 에러가 발생한다.")
    void sql_exception_error_code() {
        String sql = "select bad grammar";

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();
        } catch (SQLException e) {
            assertThat(e.getErrorCode()).isEqualTo(ErrorCode.COLUMN_NOT_FOUND_1);
            int errorCode = e.getErrorCode();
            log.error("errorCode={}", errorCode);
            log.error(e.toString(), e);
        }
    }

    /**
     * <code>sql-error-codes.xml</code> 파일에 각 DBMS 에러 코드들을 매핑해놓았다.
     *
     * @see org.springframework.jdbc.support.sql-error-codes.xml
     * @see <a href="https://github.com/spring-projects/spring-framework/blob/v5.3.19/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml">sql-error-codes.xml</a>
     * @see SQLErrorCodeSQLExceptionTranslator
     */
    @Test
    @DisplayName("SQL 구문이 잘못된 경우 42122 에러가 발생한다.")
    void exception_translator() {
        String sql = "select bad grammar";

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.executeQuery();
        } catch (SQLException ex) {
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.COLUMN_NOT_FOUND_1);

            // 코드 없이도 에러를 처리할 수 있다.
            SQLErrorCodeSQLExceptionTranslator exceptionTranslator =
                    new SQLErrorCodeSQLExceptionTranslator(dataSource);
            DataAccessException resultEx = exceptionTranslator.translate("잘못된 SQL", sql, ex);
            log.error("resultEx", resultEx);
            assertThat(resultEx).isInstanceOf(BadSqlGrammarException.class);
        }

    }
}
