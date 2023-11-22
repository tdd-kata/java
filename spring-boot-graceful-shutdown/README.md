# Graceful Shutdown

```yaml
# application.yaml
server:
  shutdown: graceful
spring:
  lifecycle:
    timeout-per-shutdown-phase: 20s
```

```shell
curl localhost:8080/process/15
# Start process for 15 seconds
```

- `SIGKILL(9)`은 프로세스를 즉시 종료시키기 때문에 Graceful Shutdown 할 수 없다.

```shell
# 15) SIGTERM
kill -TERM <pid>

# [ionShutdownHook] org.springframework.boot.web.embedded.tomcat.GracefulShutdown:
# Commencing graceful shutdown. Waiting for active requests to complete
```

```shell
# Process for 15.0 seconds

# [ionShutdownHook] org.springframework.boot.web.embedded.tomcat.GracefulShutdown:
# Graceful shutdown complete
```
