package com.markruler.querydsl.entity;

import com.markruler.querydsl.dto.MemberDto;
import com.markruler.querydsl.dto.MemberProjectionDto;
import com.markruler.querydsl.dto.QMemberProjectionDto;
import com.markruler.querydsl.dto.UserDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.markruler.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Querydsl 중급 문법")
@SpringBootTest
@Transactional
// @org.springframework.test.annotation.Rollback
// @org.springframework.test.annotation.Commit
class Querydsl02AdvancedTest {

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

        // em.flush();
        // em.clear();
    }

    @AfterEach
    void cleanup() {
        em.close();
    }

    /**
     * Projection: SELECT할 대상을 지정한다.
     */
    @Nested
    @DisplayName("Projection")
    class Describe_projection {

        @Test
        @DisplayName("컬럼 하나 조회")
        void sut_projection_one() {
            List<String> fetchUsernames = queryFactory
                    .select(member.username)
                    .from(member)
                    .fetch();

            assertThat(fetchUsernames).hasSize(4);
        }

        @Test
        @DisplayName("컬럼 여러 개를 Tuple로 조회")
        void sut_projection_multiple_column_with_tuple() {
            List<com.querydsl.core.Tuple> fetchUsernames = queryFactory
                    .select(member.username, member.age)
                    .from(member)
                    .fetch();

            assertThat(fetchUsernames).hasSize(4);
        }

        @Nested
        @DisplayName("컬럼 여러 개를 DTO로 조회")
        class Context_with_dto {

            @Test
            @DisplayName("순수 JPA에서 JPQL로 작성하면 코드가 지저분해진다")
            void sut_projection_multiple_column_by_JPQL() {
                List<MemberDto> resultList = em.createQuery("select new com.markruler.querydsl.dto.MemberDto(m.username, m.age) " +
                                        "from Member m",
                                MemberDto.class)
                        .getResultList();

                assertThat(resultList).hasSize(4);
                assertThat(resultList.get(0).getUsername()).isEqualTo("member1");
                assertThat(resultList.get(0).getAge()).isEqualTo(10);
            }

            @Test
            @DisplayName("Querydsl - Getter, Setter를 통해 프로퍼티 접근")
            void sut_projection_multiple_column_by_Querydsl_property() {
                List<MemberDto> fetchMember = queryFactory
                        .select(Projections.bean(MemberDto.class,
                                member.username,
                                member.age))
                        .from(member)
                        .fetch();

                assertThat(fetchMember).hasSize(4);
                assertThat(fetchMember.get(0).getUsername()).isEqualTo("member1");
                assertThat(fetchMember.get(0).getAge()).isEqualTo(10);
            }

            @Test
            @DisplayName("Querydsl - Getter, Setter 없이 필드 직접 접근")
            void sut_projection_multiple_column_by_Querydsl_field() {
                List<MemberDto> fetchMember = queryFactory
                        .select(Projections.fields(MemberDto.class,
                                member.username,
                                member.age))
                        .from(member)
                        .fetch();

                assertThat(fetchMember).hasSize(4);
                assertThat(fetchMember.get(0).getUsername()).isEqualTo("member1");
                assertThat(fetchMember.get(0).getAge()).isEqualTo(10);
            }

            @Test
            @DisplayName("Querydsl - 생성자 접근")
            void sut_projection_multiple_column_by_Querydsl_constructor() {
                List<MemberDto> fetchMember = queryFactory
                        .select(Projections.constructor(MemberDto.class,
                                member.username.as("name"), // alias 사용해도 값 잘 들어감
                                member.age))
                        .from(member)
                        .fetch();

                assertThat(fetchMember).hasSize(4);
                assertThat(fetchMember.get(0).getUsername()).isEqualTo("member1");
                assertThat(fetchMember.get(0).getAge()).isEqualTo(10);
            }

            @Test
            @DisplayName("Querydsl - 필드명이 다른 생성자 접근")
            void sut_projection_multiple_column_by_Querydsl_constructor_with_different_name() {

                final QMember memberSub = new QMember("memberSub");

                /*
                    select
                        member1.username as name,
                        (select max(memberSub.age) from Member memberSub) as age
                    from
                        Member member1
                 */
                List<UserDto> fetchMember = queryFactory
                        .select(Projections.constructor(UserDto.class,
                                member.username.as("name"), // alias 사용

                                ExpressionUtils.as(JPAExpressions
                                        .select(memberSub.age.max())
                                        .from(memberSub), "age")
                        ))
                        .from(member)
                        .fetch();

                assertThat(fetchMember).hasSize(4);
                assertThat(fetchMember.get(0).getUsername()).isEqualTo("member1");
                assertThat(fetchMember.get(0).getAge()).isEqualTo(40);
                assertThat(fetchMember.get(1).getUsername()).isEqualTo("member2");
                assertThat(fetchMember.get(1).getAge()).isEqualTo(40);
            }
        }

        @Test
        @DisplayName("@QueryProjection : Projection을 위한 Q-Type DTO를 생성할 수 있다")
        void sut_query_projection_annotation() {
            // gradle clean compileQuerydsl

            /*
                select
                    member1.username,
                    member1.age
                from
                    Member member1
             */
            List<MemberProjectionDto> fetchMember = queryFactory
                    .select(new QMemberProjectionDto(member.username, member.age))
                    .from(member)
                    .fetch();

            assertThat(fetchMember).hasSize(4);
            assertThat(fetchMember.get(0).getUsername()).isEqualTo("member1");
            assertThat(fetchMember.get(0).getAge()).isEqualTo(10);
        }

    }

    /**
     * @see <a href="https://querydsl.com/static/querydsl/5.0.0/reference/html_single/#d0e2053">Complex predicates - Querydsl Docs</a>
     */
    @Nested
    @DisplayName("동적 쿼리 생성 시 BooleanBuilder를 사용할 경우")
    class Describe_dynamic_expressions_using_BooleanBuilder {

        private List<Member> searchMember(String usernameCondition, Integer ageCondition) {

            // 동적 쿼리 생성
            BooleanBuilder builder = new BooleanBuilder();

            if (usernameCondition != null) {
                builder.and(member.username.eq(usernameCondition));
            }

            if (ageCondition != null) {
                builder.and(member.age.eq(ageCondition));
            }

            return queryFactory
                    .selectFrom(member)
                    .where(builder)
                    .fetch();
        }

        @Nested
        @DisplayName("매개 변수가 모두 null이 아니라면")
        class Context_with_parameters {

            @Test
            @DisplayName("모든 조건을 검사한다")
            void sut_query_should_create_where_username_age() {
                final String usernameParam = "member1";
                final Integer ageParam = 10;

                List<Member> members = searchMember(usernameParam, ageParam);
                assertThat(members).hasSize(1);
            }
        }

        @Nested
        @DisplayName("매개 변수 값이 null이라면")
        class Context_with_username_condition {

            @Test
            @DisplayName("해당 조건을 무시한다")
            void sut_query_should_create_where_username() {
                final String usernameParam = "member1";
                final Integer ageParam = null;

                List<Member> members = searchMember(usernameParam, ageParam);
                assertThat(members).hasSize(1);
            }
        }
    }

    /**
     * @see <a href="https://querydsl.com/static/querydsl/5.0.0/reference/html_single/#d0e2053">Complex predicates - Querydsl Docs</a>
     */
    @Nested
    @DisplayName("동적 쿼리 생성 시 where 절에 다중 파라미터를 입력할 경우")
    class Describe_complex_expressions {

        /*
            - 잘게 쪼개진 메서드들을 다른 쿼리에서도 재활용할 수 있다!!!
            - 쿼리의 가독성이 높아진다.
         */
        private List<Member> searchMember(final String usernameCondition,
                                          final Integer ageCondition) {
            return queryFactory
                    .selectFrom(member)
                    .where(usernameEq(usernameCondition),
                            ageEq(ageCondition))
                    .fetch();
        }

        private Predicate usernameEq(final String usernameCondition) {
            return usernameCondition != null ? member.username.eq(usernameCondition) : null;
        }

        private Predicate ageEq(Integer ageCondition) {
            return ageCondition != null ? member.age.eq(ageCondition) : null;
        }

        @Nested
        @DisplayName("매개 변수가 모두 null이 아니라면")
        class Context_with_parameters {

            @Test
            @DisplayName("모든 조건을 검사한다")
            void sut_query_should_create_where_username_age() {
                final String usernameParam = "member1";
                final Integer ageParam = 10;

                /*
                    select
                        member1
                    from
                        Member member1
                    where
                        member1.username = ?1
                        and member1.age = ?2
                 */
                List<Member> members = searchMember(usernameParam, ageParam);
                assertThat(members).hasSize(1);
            }
        }

        @Nested
        @DisplayName("매개 변수 값이 null이라면")
        class Context_with_username_condition {

            @Test
            @DisplayName("해당 조건을 무시한다")
            void sut_query_should_create_where_username() {
                final String usernameParam = "member1";
                final Integer ageParam = null;

                /*
                    select
                        member1
                    from
                        Member member1
                    where
                        member1.username = ?1
                 */
                List<Member> members = searchMember(usernameParam, ageParam);
                assertThat(members).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("Bulk Operation은 영속성 컨텍스트를 거치지 않는다")
    class Describe_bulk_operation {

        @Test
        @DisplayName("영속성 컨텍스트를 비우지 않으면 캐시된 엔터티를 조회한다")
        void sut_find_entity_first_level_cache() {

            // member1 = 10 -> DB : member1
            // member2 = 20 -> DB : member2
            // member3 = 30 -> DB : member3
            // member4 = 40 -> DB : member4

            long count = queryFactory
                    .update(member)
                    .set(member.username, "비회원")
                    .where(member.age.lt(28))
                    .execute();

            assertThat(count).isEqualTo(2);

            // member1 = 10 -> DB : 비회원
            // member2 = 20 -> DB : 비회원
            // member3 = 30 -> DB : member3
            // member4 = 40 -> DB : member4

            // 벌크 연산은 영속성 컨텍스트를 거치지 않고 바로 DB에 반영된다.
            // JPA를 통해 엔터티를 검색할 경우 영속성 컨텍스트에 캐시가 있다면
            // 캐싱 데이터를 가져오기 때문에 정보에 차이가 발생할 수 있다.

            List<Member> fetchMembers = queryFactory
                    .selectFrom(member)
                    .fetch();

            assertThat(fetchMembers).hasSize(4);
            assertThat(fetchMembers.get(0).getUsername()).isEqualTo("member1");
            assertThat(fetchMembers.get(1).getUsername()).isEqualTo("member2");
            assertThat(fetchMembers.get(2).getUsername()).isEqualTo("member3");
            assertThat(fetchMembers.get(3).getUsername()).isEqualTo("member4");
        }

        @Test
        @DisplayName("영속성 컨텍스트를 비우면 DB에 저장된 데이터 조회한다")
        void sut_find_entity_database() {

            // member1 = 10 -> DB : member1
            // member2 = 20 -> DB : member2
            // member3 = 30 -> DB : member3
            // member4 = 40 -> DB : member4

            long count = queryFactory
                    .update(member)
                    .set(member.username, "비회원")
                    .where(member.age.lt(28))
                    .execute();

            assertThat(count).isEqualTo(2);

            // member1 = 10 -> DB : 비회원
            // member2 = 20 -> DB : 비회원
            // member3 = 30 -> DB : member3
            // member4 = 40 -> DB : member4

            // 영속성 컨텍스트를 비우면 캐싱 데이터가 없기 때문에
            // 데이터베이스에서 다시 조회한다.
            em.flush();
            em.clear();

            List<Member> fetchMembers = queryFactory
                    .selectFrom(member)
                    .fetch();

            assertThat(fetchMembers).hasSize(4);
            assertThat(fetchMembers.get(0).getUsername()).isEqualTo("비회원");
            assertThat(fetchMembers.get(1).getUsername()).isEqualTo("비회원");
            assertThat(fetchMembers.get(2).getUsername()).isEqualTo("member3");
            assertThat(fetchMembers.get(3).getUsername()).isEqualTo("member4");
        }

    }

    @Nested
    @DisplayName("SQL Functions")
    class Describe_sql_functions {

        @Test
        @DisplayName("replace")
        void sut_replace() {
            List<String> fetchUsernames = queryFactory
                    .select(Expressions.stringTemplate(
                            "function('replace', {0}, {1}, {2})",
                            member.username, "member", "M"))
                    .from(member)
                    .fetch();

            assertThat(fetchUsernames).hasSize(4);
            assertThat(fetchUsernames)
                    .filteredOn(username -> username.equals("M1"))
                    .hasSize(1);
            assertThat(fetchUsernames).element(1).isEqualTo("M2");
            assertThat(fetchUsernames).element(2).isEqualTo("M3");
            assertThat(fetchUsernames).element(3).isEqualTo("M4");
        }


        @Test
        @DisplayName("lower")
        void sut_lower() {
        /*
            select member1.username
            from Member member1
            where member1.username = function('lower', member1.username)
         */
            List<String> fetchUsernames = queryFactory
                    .select(member.username)
                    .from(member)
                    // 간단한 ANSI function 들은 이미 Querydsl에 구현되어 있다.
                    // .where(member.username.eq(member.username.lower()))
                    .where(member.username.eq(
                            Expressions.stringTemplate(
                                    "function('lower', {0})",
                                    member.username)))
                    .fetch();

            assertThat(fetchUsernames).hasSize(4);
            assertThat(fetchUsernames).element(0).isEqualTo("member1");
            assertThat(fetchUsernames).element(1).isEqualTo("member2");
            assertThat(fetchUsernames).element(2).isEqualTo("member3");
            assertThat(fetchUsernames).element(3).isEqualTo("member4");
        }

    }

}
