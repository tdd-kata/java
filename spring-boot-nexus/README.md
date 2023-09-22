# Spring Boot with Docker

## SDKMAN

```shell
sdk list java
sdk install java 17.0.6-zulu
```

## Build BootJar

```shell
JAVA_HOME=$HOME/.sdkman/candidates/java/17.0.6-zulu ./gradlew clean assemble -i
# java -jar ./apiserver/build/libs/apiserver.jar
```

## Running the application

### Docker

`--progress=plain`을 설정해야 모든 container output을 확인할 수 있다.
아니면 자동(`auto`)으로 축약해버린다.

- [참조](https://docs.docker.com/engine/reference/commandline/buildx_build/#options)

```shell
sudo docker compose -f ./scripts/docker-compose.yaml build --no-cache --progress=plain
```

### Gradle Wrapper

```shell
JAVA_HOME=$HOME/.sdkman/candidates/java/17.0.6-zulu ./gradlew :apiserver:bootRun
```
