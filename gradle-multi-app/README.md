# Multi Application Project with Gradle

```shell
./gradlew projects

Root project 'gradle-multi-app'
+--- Project ':admin'
+--- Project ':aggregate'
+--- Project ':demo'
\--- Project ':search'
```

## modules

- aggregation
- search

## 2개의 application

- demo
- admin

## jar 빌드 후 실행

```shell
./gradlew clean :demo:build
java -jar demo/build/libs/demo-0.0.1-SNAPSHOT.jar
```

```shell
./gradlew clean :admin:build
java -jar admin/build/libs/admin-0.0.1-SNAPSHOT.jar
```

## 테스트 환경

- [Bootrun](https://docs.spring.io/spring-boot/docs/2.7.3/gradle-plugin/api/org/springframework/boot/gradle/tasks/run/BootRun.html):
  Custom JavaExec task for running a Spring Boot application.
  - executable jar 파일을 생성하고 실행한다.

```shell
./gradlew clean :demo:bootRun
./gradlew clean :admin:bootRun
```

## Checkstyle

- [The Checkstyle Plugin](https://docs.gradle.org/7.5.1/userguide/checkstyle_plugin.html) - Gradle 7.5.1

```shell
#./gradlew check
./gradlew :admin:check
```

```shell
./gradlew clean test
```

## 참조

- [Gradle Multi-Project Build](https://docs.gradle.org/7.5.1/userguide/multi_project_builds.html)
- [멀티모듈 설계 이야기 with Spring, Gradle](https://techblog.woowahan.com/2637/) - 권용근, 우아한형제들
- [(레거시 시스템) 개편의 기술 - 배달 플랫폼에서 겪은 N번의 개편 경험기](https://www.inflearn.com/course/infcon2022/unit/126502) - 권용근, 우아한형제들
- [실전! 멀티 모듈 프로젝트 구조와 설계](https://www.inflearn.com/course/infcon2022/unit/126503) - 김대성, 네이버 뮤직 플랫폼
