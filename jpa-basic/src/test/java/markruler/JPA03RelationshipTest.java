package markruler;

import markruler.domain.MemberSequence;
import markruler.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("연관관계 매핑")
class JPA03RelationshipTest {

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
    @DisplayName("단방향 연관관계(Unidirectional Relationship) 매핑")
    void sut_unidirectional_relationship_mapping() throws Exception {

        final String username = "mark";
        final String teamname = "back-end";

        try {
            final var team = new Team();
            team.setName(teamname);
            em.persist(team);

            final var member = new MemberSequence();
            member.setUsername(username);
            member.setTeam(team);
            em.persist(member);

            // FetchType.LAZY 확인을 위해 Flush
            em.flush();
            em.clear();

            final var findMember = em.find(MemberSequence.class, 1L);
            assertThat(findMember.getUsername()).isEqualTo(username);

            // Eager Loading
            // final Long findMemberTeamId = findMember.getTeamId();
            // final Team findTeam = em.find(Team.class, findMemberTeamId);

            System.out.println(">>>>>>>>>>>>>>>>>>>>> Lazy Loading");
            final Team findTeam = findMember.getTeam();
            assertThat(findTeam.getName()).isEqualTo(teamname);
            assertThat(findTeam.getMembers()).hasSize(1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
