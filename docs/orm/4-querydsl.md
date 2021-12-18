# Querydsl

- [Querydsl](#querydsl)
- [참조](#참조)
- [소개](#소개)
  - [Querydsl 설정과 검증](#querydsl-설정과-검증)
- [기본 문법](#기본-문법)

# 참조

- [Querydsl Reference Guide (latest)](https://querydsl.com/static/querydsl/latest/reference/html_single/)
  - [4.0.1 한글](https://querydsl.com/static/querydsl/4.0.1/reference/ko-KR/html_single/) - [repository](https://github.com/querydsl/querydsl.github.io/blob/f2e8f6b243d6e2f4abec342c55addd16f07d76d4/static/querydsl/4.0.1/reference/ko-KR/html_single/index.html)
- [실전! Querydsl](https://www.inflearn.com/course/Querydsl-%EC%8B%A4%EC%A0%84/dashboard) - 김영한
  - [자바 ORM 표준 JPA 프로그래밍](http://www.kyobobook.co.kr/product/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9788960777330)
- 스프링 부트에 아무런 설정도 하지 않으면 h2 DB를 메모리 모드로 JVM안에서 실행한다.

# 소개

- Querydsl은 JPQL Builder다.
- Querydsl의 핵심 원칙은 타입 안정성(Type safety)이다.
- 직접 JPQL 작성 vs. Querydsl 사용

```java
@SpringBootTest
@Transactional
public class QuerydslBasicTest {

  @PersistenceContext
  EntityManager em;

  // JPAQueryFactory를 필드로 제공하면 동시성 문제는 어떻게 될까?
  // 결론은 동시성 문제는 걱정하지 않아도 된다.
  // JPAQueryFactory를 생성할 때 EntityManager가 필요하다.
  // 그런데 스프링 프레임워크는 여러 쓰레드에서 동시에 같은 EntityManager에 접근해도
  // 트랜잭션마다 별도의 영속성 컨텍스트를 제공한다.
  JPAQueryFactory queryFactory;

  @BeforeEach
  void setUp() {
    queryFactory = new JPAQueryFactory(em);
  }

  // JPQL
  @Test
  public void jpql() {
    // 컴파일 타임에 오류를 찾기 어렵다.
    String qlString =
      "select m from Member m " +
      "where m.username = :username";
    
    Member findMember = em.createQuery(qlString, Member.class)
            .setParameter("username", "member1")
            .getSingleResult();
    
    assertThat(findMember.getUsername()).isEqualTo("member1");
  }
    
  // QueryDSL
  @Test
  public void querydsl() {
    // compileQuerydsl 실행
    QMember m = new QMember("m");
    
    // 컴파일 타임에 오류를 찾기 쉽다.
    Member findMember = queryFactory
            .select(m)
            .from(m)
            .where(usernameEq("member1")) // 파라미터 바인딩 자동 처리
            .fetchOne();
    
    assertThat(findMember.getUsername()).isEqualTo("member1");
  }
    
  private BooleanExpression usernameEq(String username) {
    return member.username.eq(username);
  }

}
```

## Querydsl 설정과 검증

Querydsl을 이용해서 쿼리를 작성하려면 Q-Type(Querydsl query type)이 필요하다. 공식 문서에서는 쿼리 타입([query type](https://querydsl.com/static/querydsl/5.0.0/reference/html_single/#d0e226))이라고 부른다.

```groovy
plugins {
  id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
}

dependency {
  implementation 'com.querydsl:querydsl-jpa'
}

def querydslDir = "$buildDir/generated/querydsl"
querydsl {
  jpa = true
  querydslSourcesDir = querydslDir
}
sourceSets {
  main.java.srcDir querydslDir
}
configurations {
  querydsl.extendsFrom compileClasspath
}
compileQuerydsl {
  options.annotationProcessorPath = configurations.querydsl
}
```

```bash
./gradlew clean compileQuerydsl
```

# 기본 문법

```java
QCustomer customer = QCustomer.customer;
JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
Customer bob = queryFactory
                  .selectFrom(customer)
                  .where(customer.firstName.eq("Bob"))
                  .uniqueResult(customer);
```
