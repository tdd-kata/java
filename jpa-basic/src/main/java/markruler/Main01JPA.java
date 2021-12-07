package markruler;

import markruler.domain.MemberSequence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main01JPA {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // new entity
            var member = new MemberSequence();
            // member.setId(1L);
            member.setUsername("mark");

            // insert
            em.persist(member);
            tx.commit();

            // select
            var findMember = em.find(MemberSequence.class, 1L);
            System.out.println("findMember : " + findMember);

            // update
            tx.begin();
            findMember.setUsername("hollyeye");
            tx.commit();

            // delete
            tx.begin();
            em.remove(findMember);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
