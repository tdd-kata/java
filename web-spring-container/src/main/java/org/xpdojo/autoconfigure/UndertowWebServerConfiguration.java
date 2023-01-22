package org.xpdojo.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

// @Configuration
@ConditionalOnMyClass("io.undertow.Undertow")
public class UndertowWebServerConfiguration {

    @Bean("undertowServletWebServerFactory")
    @ConditionalOnMissingBean // 해당 클래스의 Bean이 없을 때만 Bean을 생성한다.
    public ServletWebServerFactory servletWebServerFactory() {
        return new UndertowServletWebServerFactory();
    }

}
