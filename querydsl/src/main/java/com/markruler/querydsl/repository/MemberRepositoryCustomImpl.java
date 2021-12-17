package com.markruler.querydsl.repository;

import com.markruler.querydsl.dto.MemberSearchCondition;
import com.markruler.querydsl.dto.MemberTeamDto;
import com.markruler.querydsl.dto.QMemberTeamDto;
import com.markruler.querydsl.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.markruler.querydsl.entity.QMember.member;
import static com.markruler.querydsl.entity.QTeam.team;

// 이름 뒤에 "Impl"을 꼭 넣어야 한다.
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(final MemberSearchCondition condition) {
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName"))
                )
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                        // ageBetween(condition.getAgeGoe(), condition.getAgeLoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        // final QueryResults<MemberTeamDto> results = queryFactory
        List<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName"))
                )
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // .fetchResults(); // Deprecated
                .fetch();

        // final List<MemberTeamDto> content = results.getResults();
        // final long total = results.getTotal();
        // return new PageImpl<>(content, pageable, total);

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> contents = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName"))
                )
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Member> countQuery = queryFactory
                .selectFrom(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );

        // return new PageImpl<>(contents, pageable, total);
        // return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount); // Deprecated
        return PageableExecutionUtils.getPage(contents, pageable, () -> countQuery.fetch().size());
    }

    private BooleanExpression usernameEq(final String username) {
        return username != null ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(final String teamName) {
        return teamName != null ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(final Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(final Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    // Predicate를 상속한 BooleanExpression을 반환하면 and(), or() 등으로 체이닝할 수 있다.
    private BooleanExpression ageBetween(final Integer ageGoe, final Integer ageLoe) {
        if (ageGoe == null || ageLoe == null) {
            return null;
        }
        return ageGoe(ageGoe).and(ageLoe(ageLoe));
    }

}
