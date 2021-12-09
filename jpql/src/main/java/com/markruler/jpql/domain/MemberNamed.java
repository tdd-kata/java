package com.markruler.jpql.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
/*
- 정적 쿼리 : 변경할 수 없다.
- 애플리케이션 로딩 시점에 SQL 검증 - 컴파일 타임에 오류를 발견할 수 있다.
- 애플리케이션 로딩 시점에 파싱해서 계속 캐시하고 있다.
- Spring Data JPA : @Query("select m from MemberNamed m where m.username = :username")
 */
@NamedQuery(
        name = "MemberNamed.findByUsername",
        query = "select m from MemberNamed m where m.username = :username"
)
public class MemberNamed {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private int age;
    private MemberType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                // ", team=" + team + // 양방향 연관관계는 toString() 조심
                ", type=" + type +
                '}';
    }
}
