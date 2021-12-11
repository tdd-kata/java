package com.markruler.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @ToString(of = {"id", "username", "age"})
// @ToString(exclude = "team")
@ToString
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
// @NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
// public class Member extends JpaAuditEntity {
public class Member extends AuditEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team == null) {
            throw new NullPointerException("멤버는 반드시 팀에 소속되어 있어야 합니다.");
        }
        changeTeam(team);
    }

    private void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
