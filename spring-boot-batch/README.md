# Spring Batch with Spring Boot

## Scheme

- [scripts](https://github.com/spring-projects/spring-batch/tree/main/spring-batch-core/src/main/resources/org/springframework/batch/core)

## Maven

```bash
./mvnw spring-boot:run \
  -D spring-boot.run.profiles=dev
```

## JAR

```bash
./mvnw clean install

java -jar target/batch-*.jar \
    --spring.profiles.active=test \
    inputFile=input/product.csv
```
