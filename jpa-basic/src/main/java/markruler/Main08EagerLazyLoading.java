package markruler;

import markruler.domain.MemberSequence;
import markruler.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main08EagerLazyLoading {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager entityManager = emf.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            Team team = new Team("teamA");
            entityManager.persist(team);

            MemberSequence member = new MemberSequence("member1");
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            MemberSequence findMember = entityManager.find(MemberSequence.class, member.getId());

            // FetchType.EAGER 일 경우 엔터티 반환
            // class markruler.domain.Team
            // FetchType.LAZY 일 경우 프록시 객체 반환
            // class markruler.domain.Team$HibernateProxy$aM8nqcWV
            System.out.println("findMember.team = " + findMember.getTeam().getClass());

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
