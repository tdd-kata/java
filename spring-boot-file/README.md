# File Management Service

## 실행

```shell
./gradlew bootRun
```

## Servlet의 Part

`HttpServletRequest`의 `getPart()` 메서드를 사용하면 `Part`를 얻을 수 있다.

## Spring의 MultipartResolver

`application.properties`에서 `spring.servlet.multipart.enabled` 옵션의 기본값이 `true`다.
(`org.springframework.boot.autoconfigure.web.servlet.MultipartProperties`)
그럼 스프링 부트는 서블릿 컨테이너에게 multipart를 처리하도록 요청한다.

- DispatcherServlet.checkMultipart(request)
  - StandardServletMultipartResolver.resolveMultipart(request)

먼저 `DispacherServlet`에서 `MultipartResolver`를 사용한다.
`MultipartResolver`는 `HttpServletRequest`와 `MultipartRequest`를 상속한
`MultipartHttpServletRequest`를 생성한다. (`StandardMultipartHttpServletRequest`)

- StandardMultipartHttpServletRequest.parseRequest(request)
  - files.add(part.getName(), new StandardMultipartFile(part, filename));

Spring은 이 `MultipartHttpServletRequest`를 사용해서 `MultipartFile` 인터페이스를 사용할 수 있도록 해준다.
