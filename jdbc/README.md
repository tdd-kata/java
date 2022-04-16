# Java Database Connectivity API

## H2 Database 세팅

```bash
docker run -d -p 1521:1521 -p 8082:81 -v $HOME/local/h2-data:/opt/h2-data -e H2_OPTIONS=-ifNotExists --name=h2-jdbc markruler/h2
```

`http://localhost:8082` 에서 접속 확인

| Label        | Value                             |
| ------------ | --------------------------------- |
| Driver Class | org.h2.Driver                     |
| JDBC URL     | jdbc:h2:tcp://localhost:1521/test |
| User Name    | sa                                |
| Password     |                                   |
