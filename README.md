# TDD with Java

## Quickstart

### Java project

- maven project

```bash
# https://mvnrepository.com/artifact/org.apache.maven.archetypes
# mvn archetype:generate -Dfilter=maven
mvn archetype:generate \
-DarchetypeGroupId=org.apache.maven.archetypes \
-DarchetypeArtifactId=maven-archetype-quickstart \
-DarchetypeVersion=1.4 \
-DartifactId=demo \
-DgroupId=com.example \
-Dversion=0.1.0 \
-DinteractiveMode=false
```

- gradle project

```bash
gradle init
```

### Spring Initializr

- 아래 명령어를 실행하면 지정할 수 있는 옵션과 설명이 나옵니다.

```bash
curl https://start.spring.io
# :: Spring Initializr ::  https://start.spring.io
# ...
```

- maven project

```bash
curl -L https://start.spring.io/starter.zip \
-o starter.zip \
-d dependencies=web,jdbc \
-d packaging=jar \
-d javaVersion=11 \
-d bootVersion=2.4.4 \
-d type=maven-project

unzip starter.zip -d ./demo
```

- gradle project

```bash
curl -L https://start.spring.io/starter.zip \
-o starter.zip \
-d dependencies=web,jdbc \
-d packaging=jar \
-d javaVersion=11 \
-d bootVersion=2.4.4 \
-d type=gradle-project

unzip starter.zip -d ./demo
```

## References

- [테스트 주도 개발](https://www.aladin.co.kr/shop/wproduct.aspx?ItemId=37469717) - 켄트 벡
- [현실 세상의 TDD : 안정감을 주는 코드 설계 방법](https://www.fastcampus.co.kr/dev_red_ygw) - 이규원
  - [gyuwon/TDDHandsOn](https://github.com/gyuwon/TDDHandsOn)
- 테스트 주도 개발 시작하기 - 최범균
- 자바와 JUnit을 활용한 실용주의 단위 테스트 - 제프 랭어, 앤디 헌트, 데이브 토마스
- JUnit in Action (3rd) - 피터 타치브, 펠리페 레미, 빈센트 마솔, 게리 그레고리

## with Spring

- [Testing Spring Boot: Beginner to Guru](https://www.udemy.com/course/testing-spring-boot-beginner-to-guru/) - Udemy
  - [GitHub 1](https://github.com/springframeworkguru/tb2g-testing-spring)
  - [GitHub 2](https://github.com/springframeworkguru/tsbb2b-sfg-brewery)
- [spring-boot-mvc-junit4](https://youtu.be/s9vt6UJiHg4) - Sannidhi Jalukar, Madhura Bhave
  - [Bootiful Testing](https://youtu.be/1W5_tOiwEAc) - Mario Gray, Josh Long
  - [Spring Tips: Bootiful Testing](https://youtu.be/lTSJCr7xdbM)
- [Test Driven Development (TDD) - JUnit5](https://youtu.be/z6gOPonp2t0) - Amigoscode
  - [Full Course](https://amigoscode.com/p/software-testing)
  - [GitHub - branch:7-tdd](https://github.com/amigoscode/software-testing)
