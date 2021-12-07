package markruler;

import markruler.domain.MemberSequence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main04Sequence {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            var member1 = new MemberSequence();
            member1.setUsername("mark");

            var member2 = new MemberSequence();
            member2.setUsername("ics");

            var member3 = new MemberSequence();
            member3.setUsername("test");

            /**
             * Hibernate:
             *     call next value for member_seq => sequence 1만 가져옴
             * Hibernate:
             *     call next value for member_seq => sequence 51까지 가져옴
             * >>>>>>>>>>>>> member.id: 1
             * >>>>>>>>>>>>> member.id: 2
             * >>>>>>>>>>>>> member.id: 3
             */

            // DB SEQ   | Entity SEQ
            // 1        | 1
            // 51       | 2
            // 51       | 3
            // 실제 DBMS에서 Sequence 객체를 조회해보면 아래와 같이 조회된다.
            // Current value: 51
            // Increment: 50
            // 웹 서버가 여러대여도 미리 확보하는 방식이기 때문에 문제 없다.
            em.persist(member1); // 1, 51
            em.persist(member2); // from Memory
            em.persist(member3); // from Memory

            // select 없이 식별자를 알 수 있는 방법은 JDBC에서 이미 제공하고 있다.
            // SEQUENCE는 persist 시점에 sequence를 조회해서 식별자를 엔터티에 할당한 후 insert 쿼리가 실행된다.
            System.out.println(">>>>>>>>>>>>> member.id: " + member1.getId());
            System.out.println(">>>>>>>>>>>>> member.id: " + member2.getId());
            System.out.println(">>>>>>>>>>>>> member.id: " + member3.getId());

            tx.commit();

            // select (first-level cache in persistence context)
            var firstMember = em.find(MemberSequence.class, 1L);
            System.out.println("firstMember : " + firstMember);

            // select from first-level cache
            // 추가적인 select 쿼리를 생성하지 않는다.
            var cachingMember = em.find(MemberSequence.class, 1L);
            System.out.println("cachingMember : " + cachingMember);
            System.out.println("firstMember == cachingMember : " + (firstMember == cachingMember)); // true

            // select from first-level cache
            var secondMember = em.find(MemberSequence.class, 2L);
            System.out.println("secondMember : " + secondMember);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
