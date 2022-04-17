package org.xpdojo.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xpdojo.jdbc.connection.ConnectionConst;
import org.xpdojo.jdbc.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberRepositoryWithDataSourceTest {

    MemberRepositoryWithDataSource memberRepository;

    @BeforeEach
    void setDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(ConnectionConst.URL);
        dataSource.setDriverClassName(ConnectionConst.DRIVER_CLASS_NAME); // 지정하지 않아도 라이브러리에서 자동으로 찾음
        dataSource.setUsername(ConnectionConst.USERNAME);
        dataSource.setPassword(ConnectionConst.PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyConnectionPool");
        dataSource.setConnectionTimeout(1000);
        dataSource.setIdleTimeout(1000);
        // dataSource.setAutoCommit(false);
        dataSource.setConnectionTestQuery("SELECT 1");

        memberRepository = new MemberRepositoryWithDataSource(dataSource);
    }

    @Nested
    @DisplayName("Member CRUD")
    class Describe_Member_CRUD {
        final Member member = new Member("member1", 10_000);

        @Test
        @Order(1)
        @Disabled("트랜잭션이 적용되지 않음")
        @DisplayName("1개의 Connection 만으로도 Member를 저장, 조회, 수정, 삭제할 수 있다")
        void sut_member_crud() throws SQLException {
            // delete from member

            // insert
            int savedRowCount = memberRepository.save(member);
            assertThat(savedRowCount).isEqualTo(1);

            // select
            Member savedMember = memberRepository.findById(member);
            assertThat(savedMember).isEqualTo(member); // equals() 오버라이딩 필수

            // update
            int updatedRowCount = memberRepository.update(member, 20_000);
            assertThat(updatedRowCount).isEqualTo(1);
            Member updatedMember = memberRepository.findById(member);
            assertThat(updatedMember).isNotEqualTo(member); // equals() 오버라이딩 필수
            assertThat(updatedMember.getMoney()).isEqualTo(20_000);

            // delete
            int deletedRowCount = memberRepository.delete(member);
            assertThat(deletedRowCount).isEqualTo(1);
            assertThatThrownBy(() -> memberRepository.findById(member))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }
}
