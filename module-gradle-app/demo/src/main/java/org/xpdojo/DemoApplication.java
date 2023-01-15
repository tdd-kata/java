package org.xpdojo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ComponentScan(basePackages) 설정을 별도로 추가하지 않으면
 * 기본적으로 @SpringBootApplication 애노테이션이 있는 패키지가 basePackage다.
 * 다른 모듈의 Bean을 스캔하려면 동일한 레벨에 위치시킨다.
 */
// @org.springframework.context.annotation.ComponentScan(basePackages = {"org.xpdojo"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

