# JPQL

- [JPQL](#jpql)
- [참조](#참조)
- [JPA가 지원하는 다양한 쿼리](#jpa가-지원하는-다양한-쿼리)
  - [JPQL (Java Persistence Query Language)](#jpql-java-persistence-query-language)
  - [JPA Criteria](#jpa-criteria)
  - [QueryDSL](#querydsl)
  - [Native SQL](#native-sql)
  - [JDBC API, MyBatis, SpringJdbcTemplate](#jdbc-api-mybatis-springjdbctemplate)
- [JPQL 문법](#jpql-문법)
  - [집합과 정렬](#집합과-정렬)
  - [TypeQuery](#typequery)
  - [파라미터 바인딩 (Parameter Binding)](#파라미터-바인딩-parameter-binding)
    - [이름(name) 기준](#이름name-기준)
    - [위치(position) 기준](#위치position-기준)
  - [프로젝션 (Projection)](#프로젝션-projection)
  - [페이징 (Paging)](#페이징-paging)
  - [조인 (Join)](#조인-join)
  - [서브 쿼리 (Subquery)](#서브-쿼리-subquery)
  - [타입 표현(Type Expression)](#타입-표현type-expression)
  - [조건식 (CASE, COALESCE, NULLIF)](#조건식-case-coalesce-nullif)
  - [함수 (Function)](#함수-function)
  - [경로 표현식 (Path Expression)](#경로-표현식-path-expression)
  - [🧐️페치 조인 (Fetch Join)](#️페치-조인-fetch-join)
  - [다형성 쿼리 (Polymorphic Query)](#다형성-쿼리-polymorphic-query)
  - [🧐️네임드 쿼리 (Named Query)](#️네임드-쿼리-named-query)
  - [벌크 연산 (Bulk Operations)](#벌크-연산-bulk-operations)

# 참조

- [자바 ORM 표준 JPA 프로그래밍 (기본편)](https://www.inflearn.com/course/ORM-JPA-Basic/dashboard) - 김영한
  - [동명의 책](https://www.aladin.co.kr/shop/wproduct.aspx?ISBN=9788960777330)
  - [예제](https://github.com/xpdojo/java/tree/main/jpql)
- [JPQL Language Reference](https://docs.oracle.com/html/E13946_04/ejb3_langref.html) - Oracle Kodo Docs
- [Full Query Language Syntax](https://eclipse-ee4j.github.io/jakartaee-tutorial/#full-query-language-syntax) - Jakarta EE Tutorial

# JPA가 지원하는 다양한 쿼리

## JPQL (Java Persistence Query Language)

JPQL은 SQL을 추상화한 객체 지향 쿼리 언어다. SQL 문법과 유사하지만 SQL은 DB 테이블을 대상으로 쿼리하는 반면, JPQL은 엔터티 객체를 대상으로 쿼리한다.

JPQL도 결국 SQL로 변환된다.

## JPA Criteria

- 장점
  - 문자가 아닌 자바 코드로 JPQL을 작성할 수 있는 JPQL 빌더 역할을 한다. 그래서 컴파일 오류를 잡을 수 있다.
  - 동적 쿼리를 설계하기 편리하다.
- 단점
  - JPA 공식 기능이지만 가독성이 너무 좋지 않아서 실무에서는 사용하지 않는다.
  - 즉, 유지보수하기 힘들다. String을 더해서 쓰는 것보다 더 힘들다.
- Criteria 대신 QueryDSL을 권장한다.

```java
// Criteria 사용 준비
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> query = cb.createQuery(Member.class);

// 루트 클래스 (조회를 시작할 클래스)
Root<Member> m = query.from(Member.class);

// 쿼리 생성
CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
List<Member> resultList = em.createQuery(cq).getResultList();
```

## QueryDSL

Criteria 장점들을 모두 갖고 있지만 단순하고 이해하기 쉽다. Spring Data JPA를 사용하기 위해서 근본 기술인 JPA를 알아야 하듯이, QueryDSL을 사용하기 위해서는 근본 기술인 JPQL을 알아야 한다.

```java
// Case: JPQL
// "select m from Member m where m.age > 18"

// Case: QueryDSL
JPAFactoryQuery query = new JPAQueryFactory(em);
QMember m = QMember.member;

List<Member> list =
      query.selectFrom(m)
           .where(m.age.gt(18))
           .orderBy(m.name.desc())
           .fetch();
```

## Native SQL

하이버네이트 창시자 개빈 킹(Gavin King)도 모든 문제를 해결하기 위해 만든 기술이 아니라고 말했다. 대부분의 경우 JPQL이나 QueryDSL로 문제를 해결할 수 있지만, 가끔 Native SQL이 필요할 때가 있다.

예를 들어, JPQL로 해결할 수 없는 특정 DB에 의존적인 기능을 사용할 때다. (오라클의 CONNECT BY, 특정 DB만 사용하는 SQL Hint)

## JDBC API, MyBatis, SpringJdbcTemplate

JPA를 사용하면서 JDBC 커넥션을 직접 사용하거나 스프링 JdbcTemplate, MyBatis 등을 함께 사용할 수 있다. 단, 영속성 컨텍스트를 적절한 시점에 강제로 플러시(Flush) 해야 한다.

예를 들어, JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트를 수동으로 플러시한다.

# JPQL 문법

```java
List<Member> result = entityManager.createQuery(
        "select m from Member m where m.username like '%kim%'",
        Member.class
).getResultList();

for (Member member : result) {
    System.out.println("member = " + member);
}
```

- 엔터티와 속성은 대소문자를 구분한다. (Member, age)
- JPQL 키워드는 대소문자를 구분하지 않는다. (SELECT, FROM, where)
- 테이블 이름이 아닌 엔터티 이름을 사용한다.
- `as` 키워드는 생략할 수 있지만 별칭은 필수다.

## 집합과 정렬

익숙한 기능들은 대부분 제공한다.

```sql
select
  COUNT(m),
  SUM(m.age),
  AVG(m.age),
  MAX(m.age),
  MIN(m.age)
from Member m
-- GROUP BY, HAVING
-- ORDER BY
```

## TypeQuery

- `TypeQuery`는 반환 타입이 명확할 때 사용하고, `Query`는 반환 타입이 명확하지 않을 때 사용한다.
- `gertResultList()` 메서드는 결과가 하나 이상일 때 사용한다. 결과가 없을 경우 빈 리스트를 반환한다.
- `getSingleResult()` 메서드는 결과가 정확히 하나일 때 사용한다. 결과가 없거나 둘 이상일 경우 예외를 던진다.

## 파라미터 바인딩 (Parameter Binding)

### 이름(name) 기준

```java
"SELECT m FROM Member m where m.username = :username"
query.setParameter("username", usernameParam);
```

### 위치(position) 기준

순서가 변경되면 장애가 날 수 있기 때문에 실무에서 절대 사용하지 않는다.

```java
"SELECT m FROM Member m where m.username = ?1"
query.setParameter(1, usernameParam);
```

## 프로젝션 (Projection)

SELECT 절에 조회할 대상을 지정하는 것을 프로젝션(Projection)이라고 한다.

- 프로젝션 대상: 엔터티, 임베디드 타입, 스칼라(Scalar) 타입(숫자, 문자 등 기본 데이터 타입)
  - `SELECT m FROM Member m` → 엔터티 프로젝션
  - `SELECT m.team FROM Member m` → 엔터티 프로젝션
  - `SELECT m.address FROM Member m` → 임베디드 타입 프로젝션
  - `SELECT m.username, m.age FROM Member m` → 스칼라 타입 프로젝션
- DISTINCT로 중복 제거

## 페이징 (Paging)

- JPA는 페이징을 다음 두 API로 추상화했다.
  - `setFirstResult(int startPosition)` : 조회 시작 위치 (0부터 시작)
  - `setMaxResults(int maxResult)` : 조회할 데이터 수

```java
String jpql = "select m from Member m order by m.name desc";
List<Member> resultList = entityManager
    .createQuery(jpql, Member.class)
    .setFirstResult(10)
    .setMaxResults(20)
    .getResultList();
```

## 조인 (Join)

```sql
-- Inner Join (내부 조인)
SELECT m FROM Member m [INNER] JOIN m.team t

-- Outer Join (외부 조인)
SELECT m FROM Member m LEFT [OUTER] JOIN m.team t

-- Theta Join (세타 조인)
SELECT count(m) FROM Member m, Team t WHERE m.username = t.name
```

- ON 절을 활용한 조인 (JPA 2.1부터 지원)
  - 조인 대상 필터링
  - 연관관계 없는 엔터티 외부 조인 (하이버네이트 5.1부터 지원)

## 서브 쿼리 (Subquery)

- JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
- SELECT 절도 가능(하이버네이트에서 지원)
  - FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
  - 조인으로 풀 수 있으면 풀어서 해결

## 타입 표현(Type Expression)

- 문자: ‘HELLO’, ‘She’’
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: jpabook.MemberType.Admin (패키지명 포함)
- 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

## 조건식 (CASE, COALESCE, NULLIF)

- CASE : 조건 분기
- COALESCE : 하나씩 조회해서 null이 아니면 반환
- NULLIF : 두 값이 같으면 null 반환, 다르면 첫번째 값 반환

## 함수 (Function)

- 하이버네이트는 사용 전 방언(Dialect)에 추가해야 한다.
  - 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록할 수 있다.

## 경로 표현식 (Path Expression)

`.`(점)을 찍어 객체 그래프를 탐색하는 것을 말한다.

```sql
select m.username -> 상태 필드
from Member m
join m.team t -> 단일 값 연관 필드
join m.orders o -> 컬렉션 값 연관 필드
where t.name = '팀A'
```

- 상태 필드(state field): 단순히 값을 저장하기 위한 필드(ex: m.username). 경로 탐색의 끝.
- 연관 필드(association field): 연관관계를 위한 필드
  - 단일 값 연관 필드: `@ManyToOne`, `@OneToOne`, 대상이 엔티티(ex: m.team). 묵시적 내부 조인 발생. 추가적으로 탐색 가능.
  - 컬렉션 값 연관 필드: `@OneToMany`, `@ManyToMany`, 대상이 컬렉션 (ex: m.orders). 묵시적 내부조인 발생. 추가적으로 탐색할 수 없음.
    - FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 검색 가능.
- 경로 탐색을 사용한 묵시적 조인 시 항상 내부 조인이 발생한다. 조인이 발생하는 상황을 한눈에 파악하기 어렵고 튜닝을 어렵게 만들기 때문에 묵시적 조인이 발생할 경우 명시적 조인으로 변경하자.

## 🧐️페치 조인 (Fetch Join)

DBMS SQL의 Join 문법이 아니다. JPQL에서 **성능 최적화를 위해서** 제공하는 기능이다. 연관된 엔터티나 컬렉션을 **SQL 한 번에 함께 조회**하는 기능이다. 즉, 객체 그래프를 SQL 한번에 조회하는 개념이다.

- 이름과 달리 `join fetch` 이다.
- 페치 조인 대상에는 별칭을 줄 수 없다. 하이버네이트는 가능하지만 가급적 사용하지 않는다. 한꺼번에 데이터를 가져오는 것이 페치 조인의 목적인데, 누군가는 조건절을 이용해서 부분적인 데이터만 가지고 올 수 있다.
- 둘 이상의 컬렉션은 페치 조인할 수 없다.
- 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.

```sql
// JPQL
select m
from Member m
join fetch m.team

// SQL
SELECT M.*, T.*
FROM MEMBER M
INNER JOIN TEAM T ON M.TEAM_ID = T.ID
```

## 다형성 쿼리 (Polymorphic Query)

- DTYPE을 활용할 수 있다.
- `TYPE` : 조회 대상을 특정 자식으로 한정한다.
  - ex) Item 중에 Book, Movie를 조회해라

```sql
// JPQL
select i from Item i
where type(i) IN (Book, Movie)

// SQL
select i from i
where i.DTYPE in ('B', 'M')
```

- `TREAT` (JPA 2.1) : 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용한다. 자바의 타입 캐스팅과 유사하다. FROM, WHERE, SELECT(하이버네이트 지원) 절에서 사용한다.
  - ex) 부모인 Item과 자식 Book이 있다.

```sql
// JPQL
select i from Item i
where treat(i as Book).auther = 'kim'

// SQL
select i.* from Item i
where i.DTYPE = 'B' and i.auther = 'kim'
```

## 🧐️네임드 쿼리 (Named Query)

- 정적 쿼리다. 변경할 수 없다.
- 애플리케이션 로딩 시점에 SQL로 파싱해서 검증한 후 계속 캐시하고 있다. 즉, 컴파일 타임에 오류를 발견할 수 있다. 그리고 런타임에 성능상 이점이 있다.
- Spring Data JPA에서 `@Query("select m from Member m where m.username = :username")` 형태로 사용한다.

## 벌크 연산 (Bulk Operations)

- 쿼리 한 번으로 여러 테이블 로우(row)를 변경한다.
- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리한다.
  - 해결책 1: 벌크 연산을 먼저 실행한다.
  - 해결책 2: 벌크 연산을 수행한 후 영속성 컨텍스트를 초기화한다.
