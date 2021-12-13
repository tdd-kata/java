package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Member;
import com.markruler.datajpa.entity.Team;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class MemberSpec {

    /**
     * Member의 team.name으로 조인
     *
     * @param teamName 팀 이름
     * @return SPECIFICATION
     */
    public static Specification<Member> teamName(final String teamName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(teamName)) {
                return null;
            }

            Join<Member, Team> team = root.join("team", JoinType.INNER);
            return criteriaBuilder.equal(team.get("name"), teamName);
        };
    }

    /**
     * Member의 username으로 조인
     *
     * @param username 사용자 이름
     * @return SPECIFICATION
     */
    public static Specification<Member> username(final String username) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(username)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("username"), username);
        };
    }
}
