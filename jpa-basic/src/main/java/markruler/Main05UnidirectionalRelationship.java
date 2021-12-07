package markruler;

import markruler.domain.MemberSequence;
import markruler.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main05UnidirectionalRelationship {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            final var team = new Team();
            team.setName("back-end");
            em.persist(team);

            final var member = new MemberSequence();
            member.setUsername("mark");
            member.setTeam(team);
            em.persist(member);

            // FetchType.LAZY 테스트를 위해 Flush
            em.flush();
            em.clear();

            final var findMember = em.find(MemberSequence.class, 1L);
            // final Long findMemberTeamId = findMember.getTeamId();
            // final Team findTeam = em.find(Team.class, findMemberTeamId);
            // final Team findTeam = findMember.getTeam();
            System.out.println("findMember : " + findMember);
            // System.out.println("findTeam : " + findTeam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
