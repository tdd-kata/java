package markruler;

import markruler.domain.MemberSequence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main02JPQL {

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
            List<MemberSequence> members = em
                    .createQuery(
                            "select m from MemberSequence as m",
                            MemberSequence.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            for (var findMember : members) {
                System.out.println("findMember : " + findMember);
            }

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
