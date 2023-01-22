package org.xpdojo.webspringcontainer.container;

import org.springframework.context.annotation.Import;
import org.xpdojo.autoconfiguration.DispatcherServletConfiguration;
import org.xpdojo.autoconfiguration.WebServerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({WebServerConfiguration.class, DispatcherServletConfiguration.class})
public @interface EnableAutoConfiguration {
}
