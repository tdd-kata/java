# 자바 관련 라이브러리 소스 코드

## JDK

- [maven](https://repo1.maven.org/maven2/)
  - Java SE: Java Platform Standard Edition - JDK 경로에 `src.zip`으로 있음
  - [javax](https://repo1.maven.org/maven2/javax/)
    - J2EE: Java 2 Enterprise Edition (Jakarta EE) - [javaee-api](https://repo1.maven.org/maven2/javax/javaee-api/) (`javax.persistence` 등)
      - Servlet
      - JSP: Java Server Pages (Jakarta Server Pages)
      - JDBC: Java Database Connectivity
      - JNDI: Java Naming and Directory Interface
      - RMI: Remote Method Invocation
      - JTA: Java Transaction API (Jakarta Transactions)
      - EJB: Enterprise JavaBeans (Jakarta Enterprise Beans)
      - JSF: JavaServer Faces (Jakarta Server Faces)
      - JAX-RS: Java API for RESTful Web Services (Jakarta RESTful Web Services)
      - CDI: Contexts and Dependency Injection

## Test

- JUnit: Unit Testing Framework for the Java
  - 3-4: [junit3-4](https://repo1.maven.org/maven2/junit/junit/)
  - 5: [junit5](https://github.com/junit-team/junit5/releases)
- Mock
  - [mockito](https://github.com/mockito/mockito)
- Hamcrest - [JavaHamcrest](https://github.com/hamcrest/JavaHamcrest)

## Database

- JPA: Java Persistence API - [jakarta-persistence api](https://github.com/eclipse-ee4j/jpa-api/releases)
  - hibernate: JPA 구현체 - [hibernate/hibernate-orm](https://github.com/hibernate/hibernate-orm)
  - Spring Data JPA - [spring-projects/spring-data-jpa](https://github.com/spring-projects/spring-data-jpa)
- Driver
  - oracle - [ojdbc](https://www.oracle.com/database/technologies/jdbc-drivers-12c-downloads.html)
  - [mariadb](https://github.com/mariadb-corporation/mariadb-connector-j)
  - [mysql](https://github.com/mysql/mysql-connector-j)
  - [postgres](https://github.com/pgjdbc/pgjdbc)

## Spring

- [spring-projects](https://github.com/spring-projects)
  - [spring-framework](https://github.com/spring-projects/spring-framework)
  - [spring-boot](https://github.com/spring-projects/spring-boot)
  - [spring-security](https://github.com/spring-projects/spring-security)
  - [spring-kafka](https://github.com/spring-projects/spring-kafka)
  - [spring-batch](https://github.com/spring-projects/spring-batch)
  - [spring-data-redis](https://github.com/spring-projects/spring-data-redis)
