package markruler;

import markruler.domain.Address;
import markruler.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainEmbeddedValueType {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "10000");

            Member user1 = new Member();
            user1.setUsername("user1");
            user1.setHomeAddress(address);
            em.persist(user1);

            Member user2 = new Member();
            user2.setUsername("user2");
            user2.setHomeAddress(address);
            em.persist(user2);

            // 안전한 Value Type 사용을 위해 setter를 제거한다.
            user1.getHomeAddress().setCity("newCity");
            // +--+-------+----------+------+--------+
            // |ID|CITY   |POSTALCODE|STREET|USERNAME|
            // +--+-------+----------+------+--------+
            // |1 |newCity|10000     |street|user1   |
            // |2 |newCity|10000     |street|user2   |
            // +--+-------+----------+------+--------+

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
