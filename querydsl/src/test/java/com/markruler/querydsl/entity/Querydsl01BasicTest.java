package com.markruler.querydsl.entity;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

import static com.markruler.querydsl.entity.QMember.member;
import static com.markruler.querydsl.entity.QTeam.team;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Querydsl 기본 문법")
@SpringBootTest
@Transactional
// @org.springframework.test.annotation.Rollback
// @org.springframework.test.annotation.Commit
class Querydsl01BasicTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        /*
            JPAQueryFactory의 동시성 문제는 EntityManger에 의존한다.
            스프링 프레임워크는 여러 쓰레드에서 동시에 같은 EntityManager에 접근해도,
            트랜잭션마다 별도의 영속성 컨텍스트를 제공하기 때문에,
            동시성 문제는 걱정하지 않아도 된다.
         */
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @AfterEach
    void cleanup() {
        em.close();
    }

    @Test
    @DisplayName("JPQL은 쿼리에 오타가 있을 경우 런타임에 오류가 발생한다")
    void sut_jpql() {

        String qlString =
                "select m from Member m " +
                        "where m.username = :username";

        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    @DisplayName("Querydsl은 쿼리에 오타가 있을 경우 컴파일 타임에 오류가 발생한다")
    void sut_querydsl() {
        // QMember m = QMember.member;
        // JPQL의 alias를 지정할 수 있다.
        // 같은 테이블을 조인할 경우에만 사용하면 된다.
        // 대부분은 `QMember.member`(static import)를 활용한다.
        // QMember m = new QMember("m");

        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Nested
    @DisplayName("검색 조건을")
    class Describe_search {

        @Test
        @DisplayName("메서드 체인 형태로 작성할 수 있다")
        void sut_search_with_method_chaining() {

            List<Member> findMember = queryFactory
                    .selectFrom(member)
                    .where(member.username.eq("member1")
                            // .and(member.age.eq(10)))
                            .and(member.age.between(10, 30)))
                    .fetch();

            assertThat(findMember).hasSize(1);
            assertThat(findMember.get(0).getUsername()).isEqualTo("member1");
        }

        @Test
        @DisplayName("매개 변수로 전달할 수 있다")
        void sut_search_with_params() {

            Member findMember = queryFactory
                    .selectFrom(member)
                    .where(
                            member.username.eq("member1"),
                            member.age.between(10, 30)
                    )
                    .fetchOne();

            assertThat(findMember).isNotNull();
            assertThat(findMember.getUsername()).isEqualTo("member1");
        }
    }

    @Nested
    @DisplayName("결과를 조회하는 방법")
    class Describe_results {

        @Test
        @DisplayName("fetch()")
        void sut_result_fetch() {

            List<Member> fetch = queryFactory
                    .selectFrom(member)
                    .fetch();

            assertThat(fetch).hasSize(4);
        }

        @Test
        @DisplayName("fetchAll()")
        void sut_result_jpa_query() {

            JPAQuery<Member> memberJPAQuery = queryFactory
                    .selectFrom(member)
                    .fetchAll();

            assertThat(memberJPAQuery.stream().limit(1)).hasSize(1);
            assertThat(memberJPAQuery.stream().allMatch(member -> member.getUsername().contains("member"))).isTrue();
            assertThat(memberJPAQuery.fetch()).hasSize(4);
        }

        @Test
        @DisplayName("Deprecated fetchResults()")
        void sut_result_fetchResults() {

            QueryResults<Member> results = queryFactory
                    .selectFrom(member)
                    .fetchResults();

            /*
                select
                    count(member0_.member_id) as col_0_0_
                from
                    member member0_
             */
            long total = results.getTotal();
            /*
                select
                    member0_.member_id as member_i1_1_,
                    member0_.age as age2_1_,
                    member0_.team_id as team_id4_1_,
                    member0_.username as username3_1_
                from
                    member member0_
             */
            List<Member> content = results.getResults();

            assertThat(total).isEqualTo(4);
            assertThat(content).hasSize(4);
            assertThat(content.get(0).getUsername()).isEqualTo("member1");
        }

        @Test
        @DisplayName("Deprecated fetchCount()")
        void sut_result_fetchCount() {

            long count = queryFactory
                    .selectFrom(member)
                    .fetchCount();

            assertThat(count).isEqualTo(4);
        }
    }

    @Nested
    @DisplayName("orderBy 메서드는")
    class Describe_orderBy {

        final String member5Username = "member5";
        final String member6Username = "member6";

        @BeforeEach
        void setUpAdditionalMember() {
            em.persist(new Member(null, 90));
            em.persist(new Member(member5Username, 100));
            em.persist(new Member(member6Username, 120));
        }

        @Test
        @DisplayName("내림차순, 오름차순으로 조합해서 정렬할 수 있다")
        void sut_sort() {
            List<Member> fetchResult = queryFactory
                    .selectFrom(member)
                    .where(member.age.gt(80))
                    /*
                        순서대로 우선순위
                        order by
                            member0_.age desc,
                            member0_.username asc nulls last
                     */
                    .orderBy(member.age.desc(), member.username.asc().nullsLast())
                    .fetch();

            Member firstMember = fetchResult.get(0);
            Member secondMember = fetchResult.get(1);

            assertThat(firstMember.getUsername()).isEqualTo(member6Username);
            assertThat(secondMember.getUsername()).isEqualTo(member5Username);
        }
    }

    @Nested
    @DisplayName("페이징")
    class Describe_paging {

        @Test
        @DisplayName("fetch()로 결과를 조회할 수 있다")
        void fetch() {
        /*
            select
                member0_.member_id as member_i1_1_,
                member0_.age as age2_1_,
                member0_.team_id as team_id4_1_,
                member0_.username as username3_1_
            from
                member member0_
            order by
                member0_.username desc limit ? offset ?
         */
            List<Member> fetchResult = queryFactory
                    .selectFrom(member)
                    .orderBy(member.username.desc())
                    .offset(1)
                    .limit(2)
                    .fetch();

            assertThat(fetchResult).hasSize(2);
        }

        @Test
        @DisplayName("QueryResults로 결과를 조회할 수 있다")
        void queryResults() {

            QueryResults<Member> queryResults = queryFactory
                    .selectFrom(member)
                    .orderBy(member.username.desc())
                    .offset(1)
                    .limit(2)
                    .fetchResults();

        /*
            getTotal()에서 2개의 쿼리가 실행된다.

            [1]
            select
                count(member0_.member_id) as col_0_0_
            from
                member member0_

            [2]
            select
                member0_.member_id as member_i1_1_,
                member0_.age as age2_1_,
                member0_.team_id as team_id4_1_,
                member0_.username as username3_1_
            from
                member member0_
            order by
                member0_.username desc limit ? offset ?
         */
            assertThat(queryResults.getTotal()).isEqualTo(4);
            assertThat(queryResults.getLimit()).isEqualTo(2);
            assertThat(queryResults.getOffset()).isEqualTo(1);
            assertThat(queryResults.getResults().size()).isEqualTo(2);
        }

    }

    @Test
    @DisplayName("집합 함수를 사용할 수 있다")
    void sut_aggregation() {
        // 실무에서는 Tuple보다 DTO로 직접 넘겨서 사용한다. (why?)
        List<Tuple> tupleResult = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                ).from(member)
                .fetch();

        Tuple tuple = tupleResult.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
    }

    @Test
    @DisplayName("그룹 함수를 사용해서 각 팀의 이름과 평균 연령을 구할 수 있다")
    void sut_group() {
        // when
        List<Tuple> fetchTuple = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        // then
        Tuple teamA = fetchTuple.get(0);
        Tuple teamB = fetchTuple.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15); // (10 + 20) / 2
        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35); // (30 + 40) / 2
    }

    @Nested
    @DisplayName("JOIN")
    class Describe_join {

        @Test
        @DisplayName("연관관계가 있는 필드로 조인할 수 있다")
        void sut_join() {
            // when
            List<Member> teamA = queryFactory
                    .selectFrom(member)
                    // .join(member.team, team)
                    .innerJoin(member.team, team)
                    .where(team.name.eq("teamA"))
                    .fetch();

        /*
            select
                member0_.member_id as member_i1_1_,
                member0_.age as age2_1_,
                member0_.team_id as team_id4_1_,
                member0_.username as username3_1_
            from
                member member0_
            inner join
                team team1_
                    on member0_.team_id=team1_.id
            where
                team1_.name=?
         */
            // then
            assertThat(teamA)
                    .extracting("username")
                    .containsExactly("member1", "member2");
        }

        @Test
        @DisplayName("연관관계가 없는 필드로 조인할 수 있다")
        void sut_theta_join() {
            em.persist(new Member("teamA"));
            em.persist(new Member("teamB"));
            em.persist(new Member("teamC"));

            List<Member> fetchResult = queryFactory
                    .select(member)
                    .from(member, team)
                    .where(member.username.eq(team.name))
                    .fetch();

            assertThat(fetchResult)
                    .extracting("username")
                    .containsExactly("teamA", "teamB");
        }

        @Test
        @DisplayName("on 절")
        void sut_join_on_filtering() {
            // when
            List<Tuple> fetchTuple = queryFactory
                    .select(member, team)
                    .from(member)
                    .leftJoin(member.team, team)
                    .on(team.name.eq("teamA"))
                    .fetch();

            // then
            // for (Tuple tuple : fetchTuple) {
            //     System.out.println(tuple);
            // }
            Member firstMember = fetchTuple.get(0).get(QMember.member);
            Team firstTeam = fetchTuple.get(0).get(QTeam.team);

            assertThat(firstMember).isNotNull();
            assertThat(firstMember.getUsername()).isEqualTo("member1");
            assertThat(firstTeam).isNotNull();
            assertThat(firstTeam.getName()).isEqualTo("teamA");
        }

        @Test
        @DisplayName("연관관계가 없는 엔터티 간의 Outer Join - on join")
        void sut_join_on_no_relation() {
            em.persist(new Member("teamA"));
            em.persist(new Member("teamB"));
            em.persist(new Member("teamC"));

            // when
            List<Tuple> fetchTuple = queryFactory
                    .select(member, team)
                    .from(member)
                    // 일반 조인 => leftJoin(member.team, team)
                    .leftJoin(team).on(member.username.eq(team.name))
                    .fetch();

        /*
            select
                member0_.member_id as member_i1_1_0_,
                team1_.id as id1_2_1_,
                member0_.age as age2_1_0_,
                member0_.team_id as team_id4_1_0_,
                member0_.username as username3_1_0_,
                team1_.name as name2_2_1_
            from
                member member0_
            left outer join
                team team1_
                    on (
                        member0_.username=team1_.name
                    )
         */

            // then
            // for (Tuple tuple : fetchTuple) {
            //     System.out.println(tuple);
            // }
            Member firstMember = fetchTuple.get(0).get(QMember.member);
            Team firstTeam = fetchTuple.get(0).get(QTeam.team);

            assertThat(firstMember).isNotNull();
            assertThat(firstMember.getUsername()).isEqualTo("member1");
            assertThat(firstTeam).isNull();
        }

        @PersistenceUnit
        EntityManagerFactory emf;

        @Test
        @DisplayName("fetch join이 아닐 때")
        void sut_not_fetch_join() {
            em.flush();
            em.close();

            final PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();

            final String memberName = "member1";
            Member findMember = queryFactory
                    .selectFrom(member)
                    .where(member.username.eq(memberName))
                    .fetchOne();

        /*
            select
                member0_.member_id as member_i1_1_,
                member0_.age as age2_1_,
                member0_.team_id as team_id4_1_,
                member0_.username as username3_1_
            from
                member member0_
            where
                member0_.username=?
         */

            assertThat(persistenceUnitUtil.isLoaded(findMember)).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(findMember.getTeam())).as("fetch join 미적용").isFalse();

            assertThat(findMember).isNotNull();
            assertThat(findMember.getUsername()).isEqualTo(memberName);

        /*
            select
                team0_.id as id1_2_0_,
                team0_.name as name2_2_0_
            from
                team team0_
            where
                team0_.id=?
         */
            assertThat(findMember.getTeam().getName()).isEqualTo("teamA");
        }

        @Test
        @DisplayName("fetch join일 때")
        void sut_fetch_join() {
            em.flush();
            em.clear();

            final PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();

            Member findMember = queryFactory
                    .selectFrom(member)
                    .join(member.team, team).fetchJoin() // FETCH JOIN
                    .where(member.username.eq("member1"))
                    .fetchOne();

        /*
            select
                member0_.member_id as member_i1_1_0_,
                team1_.id as id1_2_1_,
                member0_.age as age2_1_0_,
                member0_.team_id as team_id4_1_0_,
                member0_.username as username3_1_0_,
                team1_.name as name2_2_1_
            from
                member member0_
            inner join
                team team1_
                    on member0_.team_id=team1_.id
            where
                member0_.username=?
         */

            assertThat(persistenceUnitUtil.isLoaded(findMember)).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(findMember.getTeam())).as("fetch join 적용").isTrue();
        }
    }

    @Nested
    @DisplayName("서브 쿼리를 생성할 수 있다")
    class Describe_sub_query {

        @Test
        @DisplayName("WHERE절")
        void sut_sub_query_where() {

            final QMember memberSub = new QMember("memberSub");

        /*
            select member1
            from Member member1
            where member1.age >= (
                select avg(memberSub.age)
                from Member memberSub
            )
         */
            List<Member> fetchResult = queryFactory
                    .selectFrom(member)
                    // GOE = Greater than Or Equal
                    // LOE = Less than Or Equal
                    .where(member.age.goe(
                            select(memberSub.age.avg())
                                    .from(memberSub)
                    ))
                    .fetch();

            assertThat(fetchResult)
                    .extracting("age")
                    .containsExactly(30, 40);
        }

        @Test
        @DisplayName("IN절")
        void sut_sub_query_in() {

            final QMember memberSub = new QMember("memberSub");

        /*
            select member1
            from Member member1
            where member1.age in (
                        select memberSub.age
                        from Member memberSub
                        where memberSub.age > ?1)
         */
            List<Member> fetchResult = queryFactory
                    .selectFrom(member)
                    .where(member.age.in(
                            select(memberSub.age)
                                    .from(memberSub)
                                    .where(memberSub.age.gt(10))
                    ))
                    .fetch();

            assertThat(fetchResult)
                    .extracting("age")
                    .containsExactly(20, 30, 40);
        }

        @Test
        @DisplayName("SELECT절")
        void sut_sub_query_select() {
            final QMember memberSub = new QMember("memberSub");

            List<Tuple> fetchMember = queryFactory
                    .select(member.username,
                            select(memberSub.age.avg())
                                    .from(memberSub))
                    .from(member)
                    .fetch();

            for (Tuple tuple : fetchMember) {
                System.out.println(tuple);
                System.out.println(tuple.get(member.username));
                System.out.println(tuple.get(1, Double.class));
            }

            assertThat(fetchMember).hasSize(4);
            assertThat(fetchMember.get(0).get(member.username)).isEqualTo("member1");
            assertThat(fetchMember.get(0).get(1, Double.class)).isEqualTo(25D);
        }

        @Disabled("JPA JPQL 서브쿼리의 한계점 때문에")
        @Test
        @DisplayName("FROM 절의 서브 쿼리(인라인 뷰)는 지원하지 않는다")
        void sut_sub_query_from_inline_view() {
            // 해결방안
            // 1. 서브쿼리를 Join으로 변경한다.
            // 2. 애플리케이션에서 쿼리를 2번 분리해서 실행한다.
            // 3. Native Query를 사용한다.
        }
    }

}
