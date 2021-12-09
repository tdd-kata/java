package markruler;

import markruler.domain.Child;
import markruler.domain.Parent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("영속성 전이")
class JPA05TransitivePersistenceTest {

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
    @DisplayName("Cascade")
    void sut_cascade() throws Exception {

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            // cascade = CascadeType.PERSIST
            // em.persist(child1);
            // em.persist(child2);

            assertThat(em.contains(parent)).isTrue();
            assertThat(em.contains(child1)).isTrue();
            assertThat(em.contains(child2)).isTrue();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Orphan Removal")
    void sut_orphan_removal() throws Exception {

        try {
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent1 = em.find(Parent.class, parent.getId());
            assertThat(findParent1.getChilds()).hasSize(2);
            // cascade = CascadeType.REMOVE
            // orphanRemoval = true
            List<Child> childs = findParent1.getChilds();
            childs.remove(0);

            Parent findParent2 = em.find(Parent.class, parent.getId());
            assertThat(findParent2.getChilds()).hasSize(1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
