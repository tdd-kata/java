# Java Database Connectivity API

## H2 Database 세팅

```sh
docker run -d --restart=always -p 9092:9092 -p 8082:8080 -v $HOME/local/h2-data:/opt/h2-data -e H2_OPTIONS=-ifNotExists --name=h2-jdbc markruler/h2:1.4.200
```

`http://localhost:8082` 에서 접속 확인

| Label        | Value                             |
| ------------ |-----------------------------------|
| Driver Class | org.h2.Driver                     |
| JDBC URL     | jdbc:h2:tcp://localhost:9092/test |
| User Name    | sa                                |
| Password     |                                   |
