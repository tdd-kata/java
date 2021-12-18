package com.markruler.querydsl.repository;

import com.markruler.querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.extensions.querydsl">QuerydslPredicateExecutor</a>
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#core.web.type-safe">Querydsl Web Support</a>
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor<Member> {

    List<Member> findByUsername(String username);

}
