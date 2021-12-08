package markruler;

import markruler.domain.MemberIdentity;
import markruler.domain.MemberSequence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("영속성 컨텍스트")
class JPA01PersistenceContextTest {

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
    @DisplayName("two entity transactions")
    void sut_two_entity_transactions() throws Exception {

        final String username1 = "mark";
        final String username2 = "hollyeye";

        try {
            // new entity
            var member = new MemberSequence();
            // member.setId(1L);
            member.setUsername(username1);

            // insert
            em.persist(member);
            tx.commit();

            // select
            var findMember = em.find(MemberSequence.class, 1L);
            assertThat(findMember.getUsername()).isEqualTo(username1);

            // update
            tx.begin();
            findMember.setUsername(username2);
            tx.commit();

            assertThat(findMember.getUsername()).isEqualTo(username2);

            // delete
            tx.begin();
            em.remove(findMember);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("first-level cache")
    void sut_first_level_cache() throws Exception {

        final String username = "mark";

        try {
            // new entity
            var member = new MemberIdentity();
            // member.setId(1L); // @GeneratedValue로 자동 생성
            member.setUsername(username);

            // insert
            em.persist(member);
            // select 없이 식별자를 알 수 있는 방법은 JDBC에서 이미 제공하고 있다.
            // IDENTITY는 persist 시점에 insert 쿼리 실행 후 식별자를 조회한다.
            assertThat(member.getId()).isEqualTo(1L);

            // tx.commit();
            em.flush();
            em.clear();

            // select (first-level cache in persistence context)
            var firstMember = em.find(MemberIdentity.class, 1L);
            assertThat(firstMember.getId()).isEqualTo(1L);
            assertThat(firstMember.getUsername()).isEqualTo(username);

            // select from first-level cache
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 추가적인 select 쿼리를 생성하지 않는다.");
            var cachingMember = em.find(MemberIdentity.class, 1L);
            assertThat(firstMember).isSameAs(cachingMember);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 추가적인 select 쿼리를 생성하지 않는다.");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
