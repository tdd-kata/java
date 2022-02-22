# Spring Framework

## 테스트 환경 세팅

### docker-compose 사용

```bash
# 실행
sudo docker-compose up -d

# 중단
sudo docker-compose down
```

### docker 사용

```bash
sudo docker run --name rdb-local -d --restart=always -p 5432:5432 \
  -e POSTGRES_DB=test \
  -e POSTGRES_USER=mark \
  -e POSTGRES_PASSWORD=ruler \
  -v $(pwd)/init.sql:/docker-entrypoint-initdb.d/init.sql \
  postgres:9.4-alpine

sudo docker run --name redis-local -d --restart=always -p 6379:6379 redis:5.0.13
```

## Changelogs

### 3.x (2009~)

- `@Configuration` 어노테이션 기반 설정 지원 (v3.0)
- `@ResponseBody` 추가 (v3.0)
- SpEL(Spring Expression Language) 도입 (v3.0)
- `@ControllerAdvice` 추가 (v3.2)

### 4.x (2013~)

- [What’s New in Spring Framework 4.x](https://docs.spring.io/spring-framework/docs/4.3.x/spring-framework-reference/htmlsingle/#spring-whats-new)
- Java 8 지원
- CGLIB 기반의 프록시가 더이상 기본 생성자를 필요로 하지 않음
- `@RestController` 추가 (v4.0)
- `@RestControllerAdvice` 추가 (v4.3)
- 생성자 주입 시 `@Autowired` 생략 가능 (v4.3)
- `@Configuration`에서 생성자 주입 가능 (v4.3)
- `@RequestMapping`을 HTTP Method에 따라 `@GetMapping`, `@PostMapping` 등 추가 (v4.3)
- WebSocket, SockJS, STOMP 메시징 지원

### 5.x (2017~)

- [What's New in Spring Framework 5.x](https://github.com/spring-projects/spring-framework/wiki/What's-New-in-Spring-Framework-5.x)
- 최소 Java 8
- Java 11 지원
- Reactive Stack 추가
- WebFlux 모듈 추가
- Kotlin 지원
- JUnit 5 지원

## Modules

### Core Container

- org.springframework:spring-core
- org.springframework:spring-beans
- org.springframework:spring-context
- org.springframework:spring-context-support
- org.springframework:spring-expression

### AOP, Instrumentation

- org.springframework:spring-aop
- org.springframework:spring-aspects
- org.springframework:spring-instrument
- org.springframework:spring-instrument-tomcat

### Messaging

- org.springframework:spring-messaging

### Data Access, Integration

- org.springframework:spring-jdbc
- org.springframework:spring-tx
- org.springframework:spring-orm
- org.springframework:spring-oxm
- org.springframework:spring-jms

### Web

- org.springframework:spring-web
- org.springframework:spring-webmvc
- org.springframework:spring-websocket

### Test

- org.springframework:spring-test
