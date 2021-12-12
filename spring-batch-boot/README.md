# Spring Batch with Spring Boot

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
