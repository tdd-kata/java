# Java

- [Java](#java)
  - [Maven project](#maven-project)
  - [Gradle project](#gradle-project)
    - [기존에 있는 Maven 프로젝트에서 Gradle로 변환](#기존에-있는-maven-프로젝트에서-gradle로-변환)
    - [현재 디렉토리에 새로운 Gradle Project 생성](#현재-디렉토리에-새로운-gradle-project-생성)
    - [실행](#실행)
    - [Wrapper만 생성](#wrapper만-생성)
  - [Spring Boot project by Spring Initializr](#spring-boot-project-by-spring-initializr)
    - [👍 Spring Boot CLI](#-spring-boot-cli)
    - [cURL](#curl)
      - [Spring Boot (Maven) project](#spring-boot-maven-project)
      - [Spring Boot (Gradle) project](#spring-boot-gradle-project)
  - [HTTPie](#httpie)
    - [Usages](#usages)
  - [cURL](#curl-1)

## Maven project

```sh
# https://mvnrepository.com/artifact/org.apache.maven.archetypes
# mvn archetype:generate -Dfilter=maven
mvn archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DartifactId=demo \
  -DgroupId=com.example \
  -Dversion=0.1.0 \
  -DinteractiveMode=false
```

## Gradle project

```sh
gradle help --task init
```

### 기존에 있는 Maven 프로젝트에서 Gradle로 변환

```sh
gradle init --type pom
```

### 현재 디렉토리에 새로운 Gradle Project 생성

```sh
mkdir demo
```

```sh
# gradle init
gradle init \
  --dsl=groovy \
  --type=java-application \
  --test-framework=junit-jupiter \
  --package=org.xpdojo.demo \
  --project-name=demo
```

### 실행

```sh
./gradlew run
# > Task :app:run
# Hello World!
```

### Wrapper만 생성

```sh
gradle wrapper
```

## Spring Boot project by Spring Initializr

### 👍 Spring Boot CLI

- [Getting started](https://docs.spring.io/spring-boot/docs/2.7.8/reference/html/getting-started.html)
- [Spring Boot CLI](https://docs.spring.io/spring-boot/docs/2.7.8/reference/html/cli.html)

```sh
# SDKMAN
# sdk list springboot
sdk install springboot 2.7.8
```

```sh
# spring help init
spring init --list
```

```sh
# spring init -d=web,jpa --type=maven-project demo
# spring init -d=web,lombok --type=gradle-project demo
spring init --boot-version=2.7.8 --java-version=11 --dependencies=web --type=gradle-project --group-id=org.xpdojo --version=0.1.0 web-spring-container
```

### cURL

요청을 보내면 입력할 수 있는 옵션과 설명이 나온다.

```sh
curl https://start.spring.io
# :: Spring Initializr ::  https://start.spring.io
# ...
```

#### Spring Boot (Maven) project

```sh
curl -L https://start.spring.io/starter.zip \
  -o starter.zip \
  -d dependencies=web,jdbc \
  -d packaging=jar \
  -d javaVersion=11 \
  -d bootVersion=2.4.4 \
  -d type=maven-project
```

```sh
unzip starter.zip -d ./demo
```

#### Spring Boot (Gradle) project

```sh
curl -L https://start.spring.io/starter.zip \
  -o starter.zip \
  -d dependencies=web,jdbc \
  -d packaging=jar \
  -d language=java \
  -d javaVersion=11 \
  -d bootVersion=2.4.4 \
  -d groupId=com.markruler \
  -d artifactId=apiserver \
  -d version=0.1.0 \
  -d type=gradle-project
```

```sh
unzip starter.zip -d ./demo
```

## HTTPie

- [Installation](https://httpie.io/docs/cli/installation)

```sh
# Universal
python -m pip install --upgrade pip wheel
python -m pip install httpie
```

```sh
# Linux Snapcraft
snap install httpie
# macOS Homebrew or Linuxbrew
brew install httpie
# Windows Chocolatey
choco install httpie
```

### Usages

- [Reference](https://httpie.io/docs/cli/usage)

```sh
http -v :8080/hello
```

```sh
http pie.dev/headers 'Header;'
```

## cURL

- [subfuzion/curl](https://gist.github.com/subfuzion/08c5d85437d5d4f00e58)

```sh
curl -X POST \
  -L -v \
  -H "Origin: http://localhost:8080" \
  -H "Content-Type: application/json" \
  # -d '{"key1":"value1", "key2":"value2"}' \
  -d @data-file.json \
  localhost:8080
```

