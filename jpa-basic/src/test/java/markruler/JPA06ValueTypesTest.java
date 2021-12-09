package markruler;

import markruler.domain.Address;
import markruler.domain.Child;
import markruler.domain.Member;
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

@DisplayName("값 타입")
class JPA06ValueTypesTest {

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
    @DisplayName("Embedded Value Type")
    void sut_embedded_value_type() throws Exception {

        final String oldCity = "oldCity";
        final String newCity = "newCity";

        try {
            final Address address = new Address(oldCity, "street", "10000");

            Member member1 = new Member();
            member1.setUsername("user1");
            member1.setHomeAddress(address);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("user2");
            member2.setHomeAddress(address);
            em.persist(member2);

            // 안전한 Value Type 사용을 위해 setter를 제거한다.
            member1.getHomeAddress().setCity(newCity);
            // +--+-------+----------+------+--------+
            // |ID|CITY   |POSTALCODE|STREET|USERNAME|
            // +--+-------+----------+------+--------+
            // |1 |newCity|10000     |street|user1   |
            // |2 |newCity|10000     |street|user2   |
            // +--+-------+----------+------+--------+

            Member findMember1 = em.find(Member.class, 1L);
            assertThat(findMember1.getHomeAddress().getCity())
                    .isEqualTo(newCity);

            Member findMember2 = em.find(Member.class, 2L);
            assertThat(findMember2.getHomeAddress().getCity())
                    .isNotEqualTo(oldCity);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Value Type Collection")
    void sut_value_type_collection() throws Exception {

        final String homeCity = "homeCity";
        final String newCity = "newCity";

        try {
            Address address = new Address(homeCity, "street", "10000");

            Member user = new Member();
            user.setUsername("user1");
            user.setHomeAddress(address);
            // +-------+----+----------+------+--------+---------+----------------+-----------+
            // |USER_ID|CITY|POSTALCODE|STREET|USERNAME|WORK_CITY|WORK_POSTAL_CODE|WORK_STREET|
            // +-------+----+----------+------+--------+---------+----------------+-----------+
            // |1      |city|10000     |street|user1   |NULL     |NULL            |NULL       |
            // +-------+----+----------+------+--------+---------+----------------+-----------+

            user.getFavoriteFoods().add("chicken");
            user.getFavoriteFoods().add("pizza");
            user.getFavoriteFoods().add("noodle");
            //+-------+---------+
            // |USER_ID|FOOD_NAME|
            // +-------+---------+
            // |1      |chicken  |
            // |1      |pizza    |
            // |1      |noodle   |
            // +-------+---------+

            user.getAddressHistory().add(new Address("old1", "street1", "1"));
            user.getAddressHistory().add(new Address("old2", "street2", "2"));
            //+-------+----+----------+-------+
            // |USER_ID|CITY|POSTALCODE|STREET |
            // +-------+----+----------+-------+
            // |1      |old1|1         |street1|
            // |1      |old2|2         |street2|
            // +-------+----+----------+-------+

            em.persist(user);

            em.flush();
            em.clear();

            System.out.println("====================== START ======================");
            Member findUser = em.find(Member.class, user.getId());
            System.out.println(findUser);

            // homeCity -> newCity
            // findUser.getHomeAddress().setCity("newCity"); // setter 사용 금지
            Address a = findUser.getHomeAddress();
            findUser.setHomeAddress(new Address(newCity, a.getStreet(), a.getPostalCode()));

            assertThat(findUser.getHomeAddress().getCity()).isEqualTo(newCity);

            // chicken -> korean food
            // Set<String>: String은 불변 객체이기 때문에 수정할 수 없다.
            findUser.getFavoriteFoods().remove("chicken");
            findUser.getFavoriteFoods().add("korean food");

            assertThat(findUser.getFavoriteFoods()).doesNotContain("chicken");
            assertThat(findUser.getFavoriteFoods()).contains("korean food");

            // 전부 삭제해버린다.
            // delete from ADDRESS where USER_ID=?
            findUser.getAddressHistory().remove(new Address("old1", "street1", "1"));
            findUser.getAddressHistory().add(new Address("newCity3", "street3", "100"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

}
