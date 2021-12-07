package markruler;

import markruler.domain.Address;
import markruler.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main12ValueTypeCollection {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("homeCity", "street", "10000");

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
            findUser.setHomeAddress(new Address("newCity", a.getStreet(), a.getPostalCode()));

            // chicken -> korean food
            // Set<String>: String은 불변 객체이기 때문에 수정할 수 없다.
            findUser.getFavoriteFoods().remove("chicken");
            findUser.getFavoriteFoods().add("korean food");

            // 전부 삭제해버린다.
            // delete from ADDRESS where USER_ID=?
            findUser.getAddressHistory().remove(new Address("old1", "street1", "1"));
            findUser.getAddressHistory().add(new Address("newCity3", "street3", "100"));

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
