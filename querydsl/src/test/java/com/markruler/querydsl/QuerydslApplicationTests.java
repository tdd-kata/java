package com.markruler.querydsl;

import com.markruler.querydsl.entity.Hello;
import com.markruler.querydsl.entity.QHello;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit // (학습용) Test에서 @Transactional이 있다면 기본적으로 Rollback 하기 때문에 Commit하도록 만든다.
class QuerydslApplicationTests {

    // @PersistenceContext // 를 사용하면 스프링이 아닌 다른 프레임워크로 전환할 수 있다.
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("Q-Type 값을 확인한다")
    void contextLoads() {

        Hello hello = new Hello();
        em.persist(hello);

        final JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // Querydsl Q-Type 동작 확인
        // QHello qHello = new QHello("h");
        QHello qHello = QHello.hello;

        Hello result = queryFactory
                .selectFrom(qHello)
                .fetchOne();

        assertThat(result).isNotNull().isEqualTo(hello);
        assertThat(result.getId()).isEqualTo(hello.getId());
    }

}
