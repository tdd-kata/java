package org.xpdojo.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.xpdojo.autoconfigure.property.EnableMyConfigurationProperties;
import org.xpdojo.autoconfigure.property.ServerProperties;

// @MyAutoConfiguration
@ConditionalOnMyClass("org.apache.catalina.startup.Tomcat")
// Import는 의도를 명확하게 표현하지 못한다.
// @Import(ServerProperties.class)
@EnableMyConfigurationProperties(ServerProperties.class)
public class TomcatWebServerConfiguration {

    // @Value("${localhost.contextPath:}")
    // private String contextPath;
    //
    // @Value("${localhost.port:8080}")
    // private int port;

    @Bean("tomcatServletWebServerFactory")
    @ConditionalOnMissingBean // 해당 클래스의 Bean이 없을 때만 Bean을 생성한다.
    public ServletWebServerFactory servletWebServerFactory(ServerProperties serverProperties) {
        TomcatServletWebServerFactory webServerFactory = new TomcatServletWebServerFactory();

        webServerFactory.setContextPath(serverProperties.getContextPath());
        webServerFactory.setPort(serverProperties.getPort());

        return webServerFactory;
    }

}
