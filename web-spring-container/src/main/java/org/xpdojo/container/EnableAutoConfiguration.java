package org.xpdojo.container;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// @Import({WebServerConfiguration.class, DispatcherServletConfiguration.class})
@Import(MyAutoConfigImportSelector.class)
public @interface EnableAutoConfiguration {
}
