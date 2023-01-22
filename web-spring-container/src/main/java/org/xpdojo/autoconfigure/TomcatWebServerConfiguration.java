package org.xpdojo.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

// @Configuration
@ConditionalOnMyClass("org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfiguration {

    @Value("${contextPath:}")
    private String contextPath;

    @Value("${port:8080}")
    private int port;

    @Bean("tomcatServletWebServerFactory")
    @ConditionalOnMissingBean // 해당 클래스의 Bean이 없을 때만 Bean을 생성한다.
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory();
        webServerFactory.setContextPath(contextPath);
        webServerFactory.setPort(port);
        return webServerFactory;
    }

}
