# Persistence API

- [Persistence API](#persistence-api)
  - [JDBC: Java Database Connectivity](#jdbc-java-database-connectivity)
  - [DataSource](#datasource)

![dataaccess-jpa.png](../images/dataaccess-jpa.png)

_출처: [TERASOLUNA Server Framework Guideline](https://terasolunaorg.github.io/guideline/5.1.1.RELEASE/en/ArchitectureInDetail/DataAccessJpa.html) - [GitHub](https://github.com/terasolunaorg/terasolunaorg.github.com)_

## JDBC: Java Database Connectivity

![jdbc-db-interaction.jpg](../images/jdbc-db-interaction.jpg)

_출처: JDBC-database Interaction - [Java Enterprise in a Nutshell](https://flylib.com/books/en/2.177.1.75/1/)_

> JDBC는 `javax.sql`와 `java.sql` 2가지 패키지로 구성됩니다.
> [API - GitHub](https://github.com/openjdk/jdk/tree/master/src/java.sql/share/classes)

- DBMS 벤더와 상관 없이 JDBC API를 사용해서 데이터에 접근할 수 있습니다.
- DBMS를 변경해야 할 경우, 해당하는 JDBC 드라이버로 교체만 하면 애플리케이션의 기존 소스 코드를 그대로 사용할 수 있습니다.

## DataSource
