# 자바와 JUnit을 활용한 실용주의 단위 테스트

- [소스 코드 압축 파일](https://pragprog.com/titles/utj2/pragmatic-unit-testing-in-java-8-with-junit/) - Pragmatic
    - [깃헙 소스 코드](https://github.com/gilbutITbook/006814) - 길벗 출판사

## Quickstart

```shell
mkdir -p src/META-INF
vi src/META-INF/persistence.xml
```

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" version="2.0"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">

  <persistence-unit name="postgres-ds" transaction-type="RESOURCE_LOCAL">
    <description>postgres persistence unit</description>
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

    <class>iloveyouboss.domain.BooleanQuestion</class>
    <class>iloveyouboss.domain.PercentileQuestion</class>

    <properties>
      <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
      <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/first"/>
      <property name="javax.persistence.jdbc.user" value="jlangr"/>
      <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
      <!--   <property name="hibernate.hbm2ddl.auto" value="create" />   -->
      <property name="hibernate.show_sql" value="false"/>
      <property name="hibernate.format_sql" value="true"/>
      <property name="hibernate.transaction.flush_before_completion" value="true"/>
      <property name="log4j.logging.level" value="ERROR"/>
    </properties>
  </persistence-unit>
</persistence>
```

```shell
sudo docker-compose up -d
```
