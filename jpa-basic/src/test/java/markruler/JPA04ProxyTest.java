package markruler;

import markruler.domain.Member;
import markruler.domain.MemberSequence;
import markruler.domain.Team;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("프록시")
class JPA04ProxyTest {

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
    @DisplayName("em.getReference()와 em.find()")
    void sut_reference_find_entity() throws Exception {

        final String username = "mark";

        try {
            final Member user = new Member();
            user.setUsername(username);
            em.persist(user);

            em.flush(); // FetchType.LAZY 확인을 위해 Flush
            em.clear();

            Member refUser = em.getReference(Member.class, user.getId()); // select 쿼리 실행하지 않음
            System.out.println("refUser = " + refUser.getClass()); // class markruler.domain.User$HibernateProxy$kgTJekuA //=> Proxy 객체

            assertThat(persistenceUnitUtil.isLoaded(refUser)).isFalse(); // refUser 초기화 여부
            // Hibernate.initialize(refUser); // 강제 초기화 => isLoaded() == true

            System.out.println("refUser.username = " + refUser.getUsername()); // select 쿼리 실행
            assertThat(persistenceUnitUtil.isLoaded(refUser)).isTrue();

            // 영속성 컨텍스트에서 조회
            Member findUser = em.find(Member.class, user.getId());
            System.out.println("findUser = " + findUser.getClass()); // class markruler.domain.User$HibernateProxy$kgTJekuA

            assertThat(persistenceUnitUtil.isLoaded(findUser)).isTrue();

            assertThat(refUser).isSameAs(findUser);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("detach")
    void sut_proxy_detached() throws Exception {

        final String username = "mark";

        try {
            final Member user = new Member();
            user.setUsername(username);
            em.persist(user);

            em.flush(); // FetchType.LAZY 확인을 위해 Flush
            em.clear();

            Member detachedRefUser = em.getReference(Member.class, user.getId());
            System.out.println("detachedRefUser = " + detachedRefUser.getClass());

            em.detach(detachedRefUser); // LazyInitializationException 발생
            // em.clear(); // LazyInitializationException 발생
            // em.close(); // 왜 정상 출력되지?

            assertThatThrownBy(detachedRefUser::getUsername)
                    .isInstanceOf(LazyInitializationException.class)
                    .hasMessageContaining("could not initialize proxy");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Eager Loading vs. Lazy Loading")
    void sut_eager_lazy_loading() throws Exception {

        final String username = "mark";

        try {
            final Team team = new Team("teamA");
            em.persist(team);

            final MemberSequence member = new MemberSequence("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            final MemberSequence findMember = em.find(MemberSequence.class, member.getId());
            assertThat(persistenceUnitUtil.isLoaded(findMember)).isTrue();

            // FetchType.EAGER 일 경우 엔터티 반환
            // class markruler.domain.Team
            // FetchType.LAZY 일 경우 프록시 객체 반환
            // class markruler.domain.Team$HibernateProxy$aM8nqcWV
            final Team findTeam = findMember.getTeam();

            System.out.println("findMember.team = " + findTeam.getClass());
            assertThat(persistenceUnitUtil.isLoaded(findTeam)).isFalse();

            System.out.println("findMember.team.name = " + findTeam.getName()); // Lazy Loading
            assertThat(persistenceUnitUtil.isLoaded(findTeam)).isTrue();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
