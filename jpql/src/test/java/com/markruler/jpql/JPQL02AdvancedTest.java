package com.markruler.jpql;

import com.markruler.jpql.domain.Member;
import com.markruler.jpql.domain.MemberNamed;
import com.markruler.jpql.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPQL 2 - Advanced")
class JPQL02AdvancedTest {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
    private final PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    void cleanup() {
        em.close();
        emf.close();
    }

    @Test
    @DisplayName("경로 표현식")
    void sut_pass_expression() throws Exception {
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            // 상태 필드 (state field) : 단순히 값을 저장하기 위한 필드(ex: m.username)
            //      - 경로 탐색의 끝
            // 연관 필드 (association field) : 연관 관계를 위한 필드.
            // - 단일 값 연관 필드: @ManyToOne, @OneToOne, 대상이 엔터티(ex: m.team)
            //      - 묵시적 내부 조인 발생. 추가 탐색 가능
            // - 컬렉션 값 연관 필드: @OneToMany, @ManyToMany, 대상이 컬렉션(ex: m.orders)
            //      - 묵시적 내부 조인 발생, 추가 탐색 불가
            //      - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

            /*
            묵시적 내부 조인이 발생하는 쿼리는 실무에서 사용하지 않는다.
            튜닝하기 너무 힘들다.
             */
            String query = "select m.team from Member m where m.username = :username";
            Team result = em
                    .createQuery(query, Team.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            assertThat(result.getName()).isEqualTo("teamA");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Lazy Loading")
    void sut_lazy_loading() throws Exception {
        final String teamNameA = "teamA";
        final String teamNameB = "teamB";

        try {
            Team teamA = new Team();
            teamA.setName(teamNameA);
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName(teamNameB);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            member2.setAge(12);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            member3.setAge(14);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m";
            List<Member> result = em
                    .createQuery(query, Member.class)
                    .getResultList();

            // 실제 사용할 때 가져온다.
            assertThat(persistenceUnitUtil.isLoaded(result.get(0).getTeam())).isFalse();
            assertThat(result.get(0).getTeam().getName()).isEqualTo(teamNameA);
            assertThat(persistenceUnitUtil.isLoaded(result.get(1).getTeam())).isTrue();
            assertThat(result.get(1).getTeam().getName()).isEqualTo(teamNameA);
            assertThat(persistenceUnitUtil.isLoaded(result.get(2).getTeam())).isFalse();
            assertThat(result.get(2).getTeam().getName()).isEqualTo(teamNameB);
            assertThat(persistenceUnitUtil.isLoaded(result.get(2).getTeam())).isTrue();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("페치 조인 Fetch Join")
    void sut_fetch_join() throws Exception {
        final String teamNameA = "teamA";
        final String teamNameB = "teamB";

        try {
            Team teamA = new Team();
            teamA.setName(teamNameA);
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName(teamNameB);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            member2.setAge(12);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            member3.setAge(14);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m join fetch m.team";
            List<Member> result = em
                    .createQuery(query, Member.class)
                    .getResultList();

            // 조인할 때 같이 가져온다.
            assertThat(persistenceUnitUtil.isLoaded(result.get(0).getTeam())).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(result.get(1).getTeam())).isTrue();
            assertThat(persistenceUnitUtil.isLoaded(result.get(2).getTeam())).isTrue();

            assertThat(result.get(0).getTeam().getName()).isEqualTo(teamNameA);
            assertThat(result.get(1).getTeam().getName()).isEqualTo(teamNameA);
            assertThat(result.get(2).getTeam().getName()).isEqualTo(teamNameB);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("컬렉션 페치 조인과 DISTINCT")
    void sut_collection_fetch_join_distinct() throws Exception {
        final String teamNameA = "teamA";
        final String teamNameB = "teamB";

        try {
            Team teamA = new Team();
            teamA.setName(teamNameA);
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName(teamNameB);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            member2.setAge(12);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            member3.setAge(14);
            em.persist(member3);

            em.flush();
            em.clear();

            String query1 = "select t from Team t";
            List<Team> result1 = em
                    .createQuery(query1, Team.class)
                    .getResultList();

            assertThat(result1).hasSize(2);
            assertThat(result1.get(0).getName()).isEqualTo(teamNameA);
            assertThat(result1.get(1).getName()).isEqualTo(teamNameB);


            String query2 = "select t from Team t join fetch t.members";
            List<Team> result2 = em
                    .createQuery(query2, Team.class)
                    .getResultList();

            assertThat(result2).hasSize(3);
            assertThat(result2.get(0).getName()).isEqualTo(teamNameA);
            assertThat(result2.get(1).getName()).isEqualTo(teamNameA);
            assertThat(result2.get(2).getName()).isEqualTo(teamNameB);

            // 먼저 @ID를 포함한 모든 데이터가 일치해야 중복을 제거한다.
            // 추가로 애플리케이션에서 같은 식별자를 가진 엔터티를 제거한다.
            String query3 = "select distinct t from Team t join fetch t.members";
            List<Team> result3 = em
                    .createQuery(query3, Team.class)
                    .setFirstResult(0) // 컬렉션 페치 조인은 페이징 API를 사용할 수 없다.
                    .setMaxResults(2) // 컬렉션 페치 조인은 페이징 API를 사용할 수 없다.
                    .getResultList();

            assertThat(result3).hasSize(2);
            assertThat(result3.get(0).getName()).isEqualTo(teamNameA);
            assertThat(result3.get(1).getName()).isEqualTo(teamNameB);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("컬렉션 페치 조인과 Batch Size")
    void sut_collection_fetch_join_batch_size() throws Exception {
        final String teamNameA = "teamA";
        final String teamNameB = "teamB";

        try {
            Team teamA = new Team();
            teamA.setName(teamNameA);
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName(teamNameB);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t from Team t";
            List<Team> result = em
                    .createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            // assertThat(result).hasSize(2);
            // assertThat(result.get(0).getName()).isEqualTo(teamNameA);
            // assertThat(result.get(1).getName()).isEqualTo(teamNameB);

            // @BatchSize 대신 global 설정을 사용한다.
            // persistence.xml
            // <property name="hibernate.default_batch_fetch_size" value="100"/>
            //
            // Batch Size를 100 으로 설정했다면 in 절을 통해 한꺼번에 100개씩 조회한다.
            // where
            //     members0_.TEAM_ID in (
            //         ?, ?
            //     )
            //
            // N+1 문제를 해결하기 위해 사용할 수도 있다.
            assertThat(result).hasSize(2);
            for (Team team : result) {
                System.out.println("team = " + team.getName() + "|members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
            }
            /*
            - 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적이다.
            - 여러 테이블을 조인해서 엔터티가 가진 모양이 아닌 전혀 다른 결과를 내야 한다면 페치 조인보다는 일반 조인을 사용하고,
              필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과적이다.
             */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Entity를 직접 전달하는 방법")
    void sut_entity() throws Exception {
        final String teamNameA = "teamA";
        final String teamNameB = "teamB";

        try {
            Team teamA = new Team();
            teamA.setName(teamNameA);
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName(teamNameB);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select m from Member m where m = :member";
            Member result = em
                    .createQuery(query, Member.class)
                    .setParameter("member", member1) // 파라미터로 엔터티를 직접 전달
                    .getSingleResult();

            assertThat(result.getUsername()).isEqualTo("member1");

            String query2 = "select m from Member m where m.team = :team";
            List<Member> result2 = em
                    .createQuery(query2, Member.class)
                    .setParameter("team", teamA) // 파라미터로 외래키 엔터티를 직접 전달
                    .getResultList();

            assertThat(result2).hasSize(2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("NamedQuery")
    void sut_named_query() throws Exception {

        try {
            MemberNamed member1 = new MemberNamed();
            member1.setUsername("member1");
            em.persist(member1);

            MemberNamed member2 = new MemberNamed();
            member2.setUsername("member2");
            em.persist(member2);

            MemberNamed member3 = new MemberNamed();
            member3.setUsername("member3");
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "MemberNamed.findByUsername";
            MemberNamed result = em
                    .createNamedQuery(query, MemberNamed.class) // createNamedQuery()
                    .setParameter("username", "member1")
                    .getSingleResult();

            assertThat(result.getUsername()).isEqualTo("member1");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Bulk Operations")
    void sut_bulk_operations() throws Exception {

        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(0);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member1.setAge(0);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member1.setAge(0);
            em.persist(member3);

            // Flush
            String query1 = "update Member m set m.age = :age";
            int resultCount = em
                    .createQuery(query1) // Update/delete queries cannot be typed
                    .setParameter("age", 20)
                    .executeUpdate();

            assertThat(resultCount).isEqualTo(3);
            assertThat(member1.getAge()).isZero();

            String query2 = "select m from Member m";
            List<Member> result2 = em
                    .createQuery(query2, Member.class)
                    .getResultList();

            assertThat(result2).hasSize(3);
            assertThat(result2.get(0).getAge()).isZero();

            em.clear(); // 벌크 연산 후 영속성 컨텍스트 비우기

            String query3 = "select m from Member m";
            List<Member> result3 = em
                    .createQuery(query3, Member.class)
                    .getResultList();

            assertThat(result3).hasSize(3);
            assertThat(result3.get(0).getAge()).isEqualTo(20);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
