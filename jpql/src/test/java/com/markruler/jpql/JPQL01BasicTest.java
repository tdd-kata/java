package com.markruler.jpql;

import com.markruler.jpql.domain.Address;
import com.markruler.jpql.domain.Member;
import com.markruler.jpql.domain.Team;
import com.markruler.jpql.dto.MemberDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPQL 1 - Basic")
class JPQL01BasicTest {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
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
    @DisplayName("TupeQuery")
    void sut_type_query() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            // TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            Member result = em
                    .createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            // 결과가 하나 이상일 때
            // List<Member> results = query.getResultList();
            // 1. getSingleResult() 결과가 없을 때 NoResultException
            // javax.persistence.NoResultException: No entity found for query
            // 2. getSingleResult() 결과가 둘 이상일 때
            // javax.persistence.NonUniqueResultException: query did not return a unique result: 2

            // 결가가 정확히 하나일 때
            // Member result = query.getSingleResult();

            System.out.println("result = " + result);

            // Member expected = em.find(Member.class, 1124L);
            assertThat(result.getUsername()).isEqualTo("member1");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Projection - join")
    void sut_projection() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> result = em
                    .createQuery("select m from Member m", Member.class)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).hasSize(2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Entity Projection")
    void sut_entity_projection_join() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Team> result = em
                    // .createQuery("select m.team from Member m", Team.class) // 묵시적 조인 (Implicit Join)
                    // 위와 결과는 같지만 join을 명시적으로 선언하기 때문에 가독성이 좋아진다
                    .createQuery("select t from Member m join m.team t", Team.class) // 명시적 조인 (Explicit Join)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).isEmpty();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Embedded Projection")
    void sut_embedded_projection() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Address> result = em
                    .createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            // List<Address> result = em
            //         .createQuery("select a from Order o join o.address a", Address.class)
            //         .getResultList();

            System.out.println("result = " + result);

            assertThat(result).isEmpty();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Scalar type Projection")
    void sut_scalar_type_projection() throws Exception {
        final int memberSize = 2;
        final String memberName = "member";
        try {
            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            // Query
            List<MemberDTO> result = em
                    // 패키지명을 전부 적어야 해서 코드가 길어진다는 단점이 있지만 QueryDSL을 도입하면 해결된다.
                    .createQuery("select distinct new com.markruler.jpql.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).hasSize(memberSize);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Paging")
    void sut_paging() throws Exception {
        final int memberSize = 100;
        final int maxResultCount = 10;
        final String memberName = "member";
        try {
            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            // Query
            List<Member> result = em
                    .createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(maxResultCount)
                    .getResultList();

            System.out.println("result.size = " + result.size());

            assertThat(result).hasSize(maxResultCount);
            assertThat(result.get(0).getUsername()).isEqualTo(memberName + memberSize);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
