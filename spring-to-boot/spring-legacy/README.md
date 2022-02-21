# Spring Legacy Project

## 환경 세팅

- Tomcat을 설치한다.
  - [Apache Tomcat Versions](https://tomcat.apache.org/whichversion.html)
  - [Tomcat 8 Archives](https://tomcat.apache.org/download-80.cgi) > Quick Navigation > Archives

```bash
curl -LO https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.75/bin/apache-tomcat-8.5.75.tar.gz
tar -xvf apache-tomcat-8.5.75.tar.gz
```

- `${TOMCAT_HOME}/conf/web.xml` 파일을 참조해서 `WEB-INF/web.xml` 파일을 작성한다.
  - 해당 파일이 없다면 `maven-war-plugin`에서 에러가 발생한다.

```bash
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-war-plugin:3.2.1:war (default-war) on project legacy: Error assembling WAR: webxml attribute is required (or pre-existing WEB-INF/web.xml if executing in update mode) -> [Help 1]
```

- 프로젝트는 WAR(Web application ARchive) 형식으로 패키징한다.
  - 빌드 도구는 Maven을 기준으로 설명한다.
  - 파일명은 기본적으로 `ROOT.war`로 지정한다.

```bash
mvn clean install
```

- `ROOT.war` 파일을 `${TOMCAT_HOME}/webapps` 디렉터리에 복사한다.

```bash
cp target/ROOT.war ${TOMCAT_HOME}/webapps
```

- Tomcat을 실행한다.
  `startup.sh`과 `shutdown.sh` 스크립트는 결국 `catalina.sh`를 실행하기 때문에
  입맛에 맞게 사용하고 싶다면 `catalina.sh`를 실행해도 된다.

```bash
# 실행
${TOMCAT_HOME}/bin/startup.sh

# 중단
${TOMCAT_HOME}/bin/shutdown.sh
```
