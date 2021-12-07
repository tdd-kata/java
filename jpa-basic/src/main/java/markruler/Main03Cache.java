package markruler;

import markruler.domain.MemberIdentity;
import markruler.domain.MemberSequence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main03Cache {

    // persistence.xml의 persistence-unit name
    public static final String PERSISTENT_UNIT_NAME = "markruler";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENT_UNIT_NAME);
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // new entity
            var member = new MemberIdentity();
            // member.setId(1L); // @GeneratedValue로 자동 생성
            member.setUsername("mark");

            // insert
            em.persist(member);
            // select 없이 식별자를 알 수 있는 방법은 JDBC에서 이미 제공하고 있다.
            // IDENTITY는 persist 시점에 insert 쿼리 실행 후 식별자를 조회한다.
            System.out.println(">>>>>>>>>>>>> member.id: " + member.getId());

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
