package markruler;

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

@DisplayName("엔터티 매핑")
class JPA02EntityMappingTest {

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
    @DisplayName("Sequence Identity Generator")
    void sut_identity_generation_type_sequence() throws Exception {

        final String username1 = "mark";
        final String username2 = "ics";
        final String username3 = "test";

        try {
            var member1 = new MemberSequence();
            member1.setUsername(username1);

            var member2 = new MemberSequence();
            member2.setUsername(username2);

            var member3 = new MemberSequence();
            member3.setUsername(username3);

            /**
             * Hibernate:
             *     call next value for member_seq => sequence 1만 가져옴
             * Hibernate:
             *     call next value for member_seq => sequence 51까지 가져옴
             * >>>>>>>>>>>>> member.id: 1
             * >>>>>>>>>>>>> member.id: 2
             * >>>>>>>>>>>>> member.id: 3
             */

            // DB SEQ   | Entity SEQ
            // 1        | 1
            // 51       | 2
            // 51       | 3
            // 실제 DBMS에서 Sequence 객체를 조회해보면 아래와 같이 조회된다.
            // Current value: 51
            // Increment: 50
            // 웹 서버가 여러대여도 미리 확보하는 방식이기 때문에 문제 없다.
            em.persist(member1); // 1, 51
            em.persist(member2); // from Memory
            em.persist(member3); // from Memory

            // select 없이 식별자를 알 수 있는 방법은 JDBC에서 이미 제공하고 있다.
            // SEQUENCE는 persist 시점에 sequence를 조회해서 식별자를 엔터티에 할당한 후 insert 쿼리가 실행된다.
            assertThat(member1.getId()).isEqualTo(1L);
            assertThat(member2.getId()).isEqualTo(2L);
            assertThat(member3.getId()).isEqualTo(3L);

            // select (first-level cache in persistence context)
            var firstMember = em.find(MemberSequence.class, 1L);
            assertThat(firstMember.getUsername()).isEqualTo(username1);

            // select from first-level cache
            // 추가적인 select 쿼리를 생성하지 않는다.
            var cachingMember = em.find(MemberSequence.class, 1L);
            assertThat(cachingMember.getUsername()).isEqualTo(username1);
            assertThat(firstMember).isSameAs(cachingMember);

            // select from first-level cache
            var secondMember = em.find(MemberSequence.class, 2L);
            assertThat(secondMember.getUsername()).isEqualTo(username2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
