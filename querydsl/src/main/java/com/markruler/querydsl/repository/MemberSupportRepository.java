package com.markruler.querydsl.repository;

import com.markruler.querydsl.dto.MemberSearchCondition;
import com.markruler.querydsl.entity.Member;
import com.markruler.querydsl.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.markruler.querydsl.entity.QMember.member;
import static com.markruler.querydsl.entity.QTeam.team;

@Repository
public class MemberSupportRepository extends Querydsl4RepositorySupport {

    public MemberSupportRepository() {
        super(Member.class);
    }

    public List<Member> basicSelect() {
        return select(member)
                .from(member)
                .fetch();
    }

    public List<Member> basicSelectFrom() {
        return selectFrom(member)
                .fetch();
    }

    public Page<Member> searchPageByApplyPage(MemberSearchCondition condition, Pageable pageable) {
        JPAQuery<Member> query = selectFrom(member)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );

        List<Member> content = getQuerydsl().applyPagination(pageable, query)
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    /**
     * {@link #searchPageByApplyPage(MemberSearchCondition, Pageable)} 메서드와 같은 동작
     */
    public Page<Member> applyPagination(MemberSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, query ->
                query.selectFrom(member)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe())
                        )
        );
    }

    /**
     * {@link MemberRepositoryCustomImpl#searchPageComplex(MemberSearchCondition, Pageable)} 메서드와 같은 동작
     */
    public Page<Member> applyPaginationComplex(MemberSearchCondition condition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                        .selectFrom(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe())
                        ),

                countQuery -> countQuery
                        .select(member.id)
                        .from(member)
                        .leftJoin(member.team, team)
                        .where(
                                usernameEq(condition.getUsername()),
                                teamNameEq(condition.getTeamName()),
                                ageGoe(condition.getAgeGoe()),
                                ageLoe(condition.getAgeLoe())
                        )
        );
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
