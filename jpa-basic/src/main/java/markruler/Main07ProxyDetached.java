package markruler;

import markruler.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main07ProxyDetached {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member user = new Member();
            user.setUsername("user2");
            em.persist(user);

            em.flush();
            em.clear();

            Member detachedRefUser = em.getReference(Member.class, user.getId());
            System.out.println("detachedRefUser = " + detachedRefUser.getClass());

            em.detach(detachedRefUser); // LazyInitializationException
            // em.clear(); // LazyInitializationException
            // em.close(); // 왜 정상 출력되지?

            // org.hibernate.LazyInitializationException: could not initialize proxy [markruler.domain.User#1] - no Session
            System.out.println("detachedRefUser.getUsername() = " + detachedRefUser.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace(); // 현업에선 사용하지 말 것
        } finally {
            em.close();
        }

        emf.close();
    }
}
