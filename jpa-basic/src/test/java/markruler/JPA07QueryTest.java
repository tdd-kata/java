package markruler;

import markruler.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("다양한 Query")
class JPA07QueryTest {

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

    // @Disabled
    @Test
    @DisplayName("JPQL")
    void sut_jpql() throws Exception {

        final String username = "mark";

        try {
            final Member user = new Member();
            user.setUsername(username);
            em.persist(user);

            em.flush(); // FetchType.LAZY 확인을 위해 Flush
            em.clear();

            List<Member> result = em.createQuery(
                    "select m from Member m where m.username like '%mar%'",
                    Member.class
            ).getResultList();

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getUsername()).isEqualTo(username);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Criteria")
    void sut_criteria() throws Exception {

        try {
            // Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            // 루트 클래스 (조회를 시작할 클래스)
            Root<Member> m = query.from(Member.class);

            // 쿼리 생성
            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> result = em.createQuery(cq).getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }


    @Test
    @DisplayName("Native Query")
    void sut_native_query() throws Exception {

        try {
            List<Member> result = em.createNativeQuery(
                    "select USER_ID, USERNAME from TEST_USER",
                    Member.class
            ).getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
