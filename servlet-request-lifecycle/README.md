# 요청 흐름 순서

![Servlet](https://miro.medium.com/max/700/1*TOMg7ZPmHaklt4RW4JJ0Ew.png)

```bash
$ gradle bootRun
2022-01-24 01:57:06.466  INFO 103998 --- [           main] com.markruler.demo.DemoApplication       : Starting DemoApplication using Java 11.0.11 on dev with PID 103998 (/home/markruler/toy/xpdojo/java/request-flow/build/classes/java/main started by markruler in /home/markruler/toy/xpdojo/java/request-flow)
2022-01-24 01:57:06.475  INFO 103998 --- [           main] com.markruler.demo.DemoApplication       : No active profile set, falling back to default profiles: default
2022-01-24 01:57:06.961  INFO 103998 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-01-24 01:57:06.966  INFO 103998 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-01-24 01:57:06.966  INFO 103998 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-01-24 01:57:06.996  INFO 103998 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-01-24 01:57:06.996  INFO 103998 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 491 ms
2022-01-24 01:57:07.182  INFO 103998 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-01-24 01:57:07.191  INFO 103998 --- [           main] com.markruler.demo.DemoApplication       : Started DemoApplication in 0.947 seconds (JVM running for 1.342)
```

```bash
$ curl localhost:8080/invalid

2022-01-24 01:57:27.694  INFO 103998 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-01-24 01:57:27.694  INFO 103998 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-01-24 01:57:27.695  INFO 103998 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 0 ms
2022-01-24 01:57:27.698  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.filter.FirstFilter    : Request first filter...localhost
2022-01-24 01:57:27.699  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.filter.SecondFilter   : Request second filter...localhost
2022-01-24 01:57:27.708  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : Pre handle method has been called
2022-01-24 01:57:27.708  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : User IP address: 127.0.0.1
2022-01-24 01:57:27.708  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : Request Params: 
2022-01-24 01:57:27.708  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : Request Payload: Not a POST or PUT method
2022-01-24 01:57:27.708  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : Exiting Pre handle method
2022-01-24 01:57:27.713  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.aspect.LogAdvice      : Before aspect
2022-01-24 01:57:27.718 ERROR 103998 --- [nio-8080-exec-1] com.markruler.demo.aspect.LogAdvice      : AfterThrowing 404 NOT_FOUND

org.springframework.web.client.HttpClientErrorException: 404 NOT_FOUND
	at com.markruler.demo.controller.DemoController.notFound(DemoController.java:25) ~[main/:na]
	at com.markruler.demo.controller.DemoController$$FastClassBySpringCGLIB$$d385f058.invoke(<generated>) ~[main/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:783) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.aspectj.AspectJAfterThrowingAdvice.invoke(AspectJAfterThrowingAdvice.java:64) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor.invoke(AfterReturningAdviceInterceptor.java:57) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.aspectj.AspectJAfterAdvice.invoke(AspectJAfterAdvice.java:49) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor.invoke(MethodBeforeAdviceInterceptor.java:58) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753) ~[spring-aop-5.3.15.jar:5.3.15]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698) ~[spring-aop-5.3.15.jar:5.3.15]
	at com.markruler.demo.controller.DemoController$$EnhancerBySpringCGLIB$$94cc56a2.notFound(<generated>) ~[main/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205) ~[spring-web-5.3.15.jar:5.3.15]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150) ~[spring-web-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:655) ~[tomcat-embed-core-9.0.56.jar:4.0.FR]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.3.15.jar:5.3.15]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764) ~[tomcat-embed-core-9.0.56.jar:4.0.FR]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at com.markruler.demo.filter.SecondFilter.doFilter(SecondFilter.java:26) ~[main/:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at com.markruler.demo.filter.FirstFilter.doFilter(FirstFilter.java:26) ~[main/:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.3.15.jar:5.3.15]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.15.jar:5.3.15]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:197) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:540) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:382) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:895) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1732) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.56.jar:9.0.56]
	at java.base/java.lang.Thread.run(Thread.java:829) ~[na:na]

2022-01-24 01:57:27.719  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.aspect.LogAdvice      : After aspect
2022-01-24 01:57:27.720 ERROR 103998 --- [nio-8080-exec-1] c.m.d.controller.DemoControllerAdvice    : org.springframework.web.client.HttpClientErrorException: 404 NOT_FOUND
2022-01-24 01:57:27.727  INFO 103998 --- [nio-8080-exec-1] c.m.demo.interceptor.GeneralInterceptor  : After Completion method has been called
2022-01-24 01:57:27.728  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.filter.SecondFilter   : Response second filter...ISO-8859-1
2022-01-24 01:57:27.728  INFO 103998 --- [nio-8080-exec-1] com.markruler.demo.filter.FirstFilter    : Response first filter...ISO-8859-1
```
