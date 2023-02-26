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

```shell
docker compose -f ./scripts/docker-compose.yaml up --build
```

### Gradle Wrapper

```shell
JAVA_HOME=$HOME/.sdkman/candidates/java/17.0.6-zulu ./gradlew :apiserver:bootRun
```
