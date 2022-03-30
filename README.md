# Java

## Maven project

```bash
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

- gradle project

```bash
gradle init
```

## Spring Initializr

- 요청을 보내면 입력할 수 있는 옵션과 설명이 나온다.

```bash
curl https://start.spring.io
# :: Spring Initializr ::  https://start.spring.io
# ...
```

- maven project

```bash
curl -L https://start.spring.io/starter.zip \
-o starter.zip \
-d dependencies=web,jdbc \
-d packaging=jar \
-d javaVersion=11 \
-d bootVersion=2.4.4 \
-d type=maven-project

unzip starter.zip -d ./demo
```

- gradle project

```bash
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

unzip starter.zip -d ./demo
```
