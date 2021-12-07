package markruler;

import markruler.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

public class Main07Proxy {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        final EntityManager em = emf.createEntityManager();
        final PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();

        final EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member user = new Member();
            user.setUsername("user1");
            em.persist(user);

            em.flush();
            em.clear();

            // select 쿼리 실행하지 않음
            Member refUser = em.getReference(Member.class, user.getId());

            // class markruler.domain.User$HibernateProxy$kgTJekuA //=> Proxy 객체
            System.out.println("refUser = " + refUser.getClass());
            // Hibernate.initialize(refUser); // 강제 초기화 => isLoaded() == true
            System.out.println("[1] refUser 초기화 여부 : " + persistenceUnitUtil.isLoaded(refUser)); // false

            // select 쿼리 실행
            System.out.println("refUser.username = " + refUser.getUsername());
            System.out.println("[2] refUser 초기화 여부 : " + persistenceUnitUtil.isLoaded(refUser)); // true

            // 영속성 컨텍스트에서 조회
            // class markruler.domain.User$HibernateProxy$kgTJekuA
            Member findUser = em.find(Member.class, user.getId());
            System.out.println("findUser = " + findUser.getClass());

            System.out.println("refUser == findUser : " + (refUser == findUser));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("exception : " + e.getMessage());
        } finally {
            em.close();
        }

        emf.close();
    }
}
