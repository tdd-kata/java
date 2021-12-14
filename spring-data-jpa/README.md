# Spring Data JPA with Spring Boot

```bash
# H2 Database
sudo docker run -d \
  -p 1521:1521 \
  -p 81:81 \
  -v /home/data/h2-data:/opt/h2-data \
  -e H2_OPTIONS="-ifNotExists" \
  --name=local-h2 \
  markruler/h2:1.4.200

curl localhost:81
```

| 저장소    | URL                                  |
| --------- |--------------------------------------|
| 영속 볼륨 | jdbc:h2:tcp://localhost:1521/datajpa |
| 메모리 DB | jdbc:h2:mem:testdb                   |

```bash
# Gradle 6.8.3
gradle clean test
```
