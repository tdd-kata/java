# Spring Boot with Redis Lettuce

## Redis 실행

- [xpdojo/kv-store/redis](https://github.com/xpdojo/kv-store/blob/main/redis)

```shell
#sudo docker run \
#  --name=redis-local \
#  --detach \
#  --restart=always \
#  --publish 6379:6379 \
#  redis:7.0.5-alpine
```

## 테스트 실행

```shell
./gradlew clean test
```

## 애플리케이션 실행

```shell
./gradlew clean bootRun
```

## 참조

- [Spring Data Redis - 2.7.3](https://docs.spring.io/spring-data/data-redis/docs/2.7.3/reference/html/)
- [Lettuce Reference Guide - 6.2.1](https://lettuce.io/core/6.2.1.RELEASE/reference/index.html)
  - [Lettuce wiki](https://github.com/lettuce-io/lettuce-core/wiki)
- [Introduction to Lettuce – the Java Redis Client](https://www.baeldung.com/java-redis-lettuce) - baeldung
