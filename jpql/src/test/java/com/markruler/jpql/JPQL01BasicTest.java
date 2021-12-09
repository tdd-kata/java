package com.markruler.jpql;

import com.markruler.jpql.domain.Address;
import com.markruler.jpql.domain.Book;
import com.markruler.jpql.domain.Item;
import com.markruler.jpql.domain.Member;
import com.markruler.jpql.domain.MemberType;
import com.markruler.jpql.domain.Team;
import com.markruler.jpql.dto.MemberDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPQL 1 - Basic")
class JPQL01BasicTest {

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
    @DisplayName("TypeQuery")
    void sut_type_query() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            // TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            Member result = em
                    .createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            // 결과가 하나 이상일 때
            // List<Member> results = query.getResultList();
            // 1. getSingleResult() 결과가 없을 때 NoResultException
            // javax.persistence.NoResultException: No entity found for query
            // 2. getSingleResult() 결과가 둘 이상일 때
            // javax.persistence.NonUniqueResultException: query did not return a unique result: 2

            // 결가가 정확히 하나일 때
            // Member result = query.getSingleResult();

            System.out.println("result = " + result);

            // Member expected = em.find(Member.class, 1124L);
            assertThat(result.getUsername()).isEqualTo("member1");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Projection - join")
    void sut_projection() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> result = em
                    .createQuery("select m from Member m", Member.class)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).hasSize(2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Entity Projection")
    void sut_entity_projection_join() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Team> result = em
                    // .createQuery("select m.team from Member m", Team.class) // 묵시적 조인 (Implicit Join)
                    // 위와 결과는 같지만 join을 명시적으로 선언하기 때문에 가독성이 좋아진다
                    .createQuery("select t from Member m join m.team t", Team.class) // 명시적 조인 (Explicit Join)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).isEmpty();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Embedded Projection")
    void sut_embedded_projection() throws Exception {
        try {
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(12);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Address> result = em
                    .createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            // List<Address> result = em
            //         .createQuery("select a from Order o join o.address a", Address.class)
            //         .getResultList();

            System.out.println("result = " + result);

            assertThat(result).isEmpty();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Scalar type Projection")
    void sut_scalar_type_projection() throws Exception {
        final int memberSize = 2;
        final String memberName = "member";
        try {
            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            // Query
            List<MemberDTO> result = em
                    // 패키지명을 전부 적어야 해서 코드가 길어진다는 단점이 있지만 QueryDSL을 도입하면 해결된다.
                    .createQuery("select distinct new com.markruler.jpql.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            System.out.println("result = " + result);

            assertThat(result).hasSize(memberSize);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Paging")
    void sut_paging() throws Exception {
        final int memberSize = 100;
        final int maxResultCount = 10;
        final String memberName = "member";
        try {
            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            // Query
            List<Member> result = em
                    .createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(maxResultCount)
                    .getResultList();

            System.out.println("result.size = " + result.size());

            assertThat(result).hasSize(maxResultCount);
            assertThat(result.get(0).getUsername()).isEqualTo(memberName + memberSize);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Join")
    void sut_join() throws Exception {
        final int memberSize = 1;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // Query
            // String query = "select m from Member m inner join m.team t"; // inner join
            String query = "select m from Member m left outer join m.team t on m.username = t.name"; // left outer join
            // String query = "select m from Member m, Team t where m.username = t.name"; // theta join
            // String query = "select m from Member m, Team t where m.team.name = t.name"; // theta join
            List<Member> result = em
                    .createQuery(query, Member.class)
                    .getResultList();

            System.out.println("result.size = " + result.size());

            assertThat(result).hasSize(memberSize);
            assertThat(result.get(0).getUsername()).isEqualTo(memberName + memberSize);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Subquery")
    void sut_subquery() throws Exception {
        final int memberSize = 1;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            String query = "select (select avg(m1.age) from Member m1) as avgAge from Member m";
            double result = em
                    .createQuery(query, Double.class)
                    .getSingleResult();

            assertThat(result).isEqualTo(1d);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Type Expression")
    void sut_type_expression() throws Exception {
        final int memberSize = 100;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            String query = "select m.username, 'HELLO', true from Member m " +
                    // "where m.type = com.markruler.jpql.domain.MemberType.ADMIN";
                    "where m.type = :userType " +
                    "and m.age between 0 and 10";

            List<Object[]> result = em
                    .createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            assertThat(result).hasSize(10);
            assertThat(result.get(0)[0]).isEqualTo(memberName + 1);
            assertThat(result.get(0)[1]).isEqualTo("HELLO");
            assertThat(result.get(0)[2]).isEqualTo(true);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Entity Type Expression")
    void sut_entity_type_expression() throws Exception {
        final String isbn = "9788960777330";
        try {
            Book book = new Book();
            book.setIsbn(isbn);
            em.persist(book);

            em.flush();
            em.clear();

            String query = "select i from Item i where type(i) = Book ";

            List<Item> result = em
                    .createQuery(query, Item.class)
                    .getResultList();

            assertThat(result).hasSize(1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Case")
    void sut_case() throws Exception {
        final int memberSize = 10;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            String query = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                    "     when m.age >= 60 then '경로요금' " +
                    "     else '일반요금' " +
                    "end " +
                    "from Member m";

            List<String> result = em
                    .createQuery(query, String.class)
                    .getResultList();

            assertThat(result).hasSize(10);
            assertThat(result.get(0)).isEqualTo("학생요금");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("COALESCE")
    void sut_coalesce() throws Exception {
        final int memberSize = 10;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                // member.setUsername(memberName + i);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // COALESCE : 하나씩 조회해서 null이 아니면 반환
            String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> result = em
                    .createQuery(query, String.class)
                    .getResultList();

            assertThat(result).hasSize(10);
            assertThat(result.get(0)).isEqualTo("이름 없는 회원");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("NULLIF")
    void sut_nullif() throws Exception {
        final int memberSize = 10;
        final String memberName = "관리자";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // NULLIF : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            String query = "select nullif(m.username, '관리자') from Member m";
            List<String> result = em
                    .createQuery(query, String.class)
                    .getResultList();

            assertThat(result).hasSize(10);
            assertThat(result.get(0)).isNull();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Test
    @DisplayName("Functions")
    void sut_functions() throws Exception {
        final int memberSize = 10;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // String query1 = "select concat('a', 'b') from Member m";
            String query1 = "select substring(m.username, 2, 3) from Member m";
            List<String> result1 = em
                    .createQuery(query1, String.class)
                    .getResultList();

            // assertThat(result1.get(0)).isEqualTo("ab"); // concat('a', 'b')
            assertThat(result1.get(0)).isEqualTo("emb"); // substring(m.username, 2, 3)

            String query2 = "select locate('de', 'abcdefg') from Member m";
            List<Integer> result2 = em
                    .createQuery(query2, Integer.class)
                    .getResultList();

            assertThat(result2).hasSize(10);
            assertThat(result2.get(0)).isEqualTo(4); // locate('de', 'abcdefg')

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Disabled
    @Test
    @DisplayName("Custom Function")
    void sut_custom_function() throws Exception {
        final int memberSize = 10;
        final String memberName = "member";
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            for (int i = 1; i <= memberSize; i++) {
                Member member = new Member();
                member.setUsername(memberName + i);
                member.setAge(i);
                member.setType(MemberType.ADMIN);

                member.changeTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            // persistence.xml
            // <property name="hibernate.dialect" value="com.markruler.jpql.dialect.MyH2Dialect"/>
            String query = "select function('group_concat', m.username) from Member m";
            String result = em
                    .createQuery(query, String.class)
                    .getSingleResult();

            assertThat(result).isEqualTo("member1,member2,member3,member4,member5,member6,member7,member8,member9,member10"); // substring(m.username, 2, 3)

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }
}
