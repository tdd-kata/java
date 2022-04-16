package org.xpdojo.spring.beanscope.web;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

// proxyMode를 설정하면 ObjectProvider를 사용할 필요가 없어진다./
// proxyMode = ScopedProxyMode.TARGET_CLASS를 설정하면
// 스프링 컨테이너는 CGLIB라는 바이트 코드를 조작하는 라이브러리를 사용해서
// MyLogger를 상속받은 가짜 프록시 객체를 생성ㅎ나다.
// 그리고 스프링 컨테이너에 진짜 객체 대신 가짜 프록시 객체를 "myLogger"라는 beanName으로 등록한다.
// applicationContext.getBean("myLoggerProxy", MyLoggerProxy.class)로 조회해도 프록시 객체가 조회된다.
// 그래서 DI에도 이 가짜 프록시 객체가 주입된다.
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLoggerProxy {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "] " + requestURL + " - " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean initialized - " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean closed - " + this);
    }
}
