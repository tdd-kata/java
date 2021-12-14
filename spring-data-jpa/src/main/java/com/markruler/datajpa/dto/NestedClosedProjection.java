package com.markruler.datajpa.dto;

/*
    select
        member0_.username as col_0_0_,
        team1_.team_id as col_1_0_,
        team1_.team_id as team_id1_2_,
        team1_.name as name2_2_
    from
        member member0_
    left outer join
        team team1_
            on member0_.team_id=team1_.team_id
    where
        member0_.username=?
 */
public interface NestedClosedProjection {

    String getUsername();

    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
