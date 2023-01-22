package org.xpdojo.autoconfigure;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * @see org.springframework.boot.autoconfigure.condition.OnBeanCondition
 */
public class MyOnClassCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        /**
         * Environment
         */
        // var defaultServerType = "tomcat";
        // return context
        //         .getEnvironment()
        //         .getProperty("server.type", defaultServerType)
        //         .equals("tomcat");

        /**
         * ClassLoader: Spring Boot에서 일반적으로 사용하는 방식.
         * 관련 dependency를 추가하면 AutoConfiguration이 동작한다.
         *
         * <pre>
         *     implementation('org.springframework.boot:spring-boot-starter-web') {
         * 		 exclude group: 'org.springframework.boot', module: 'spring-boot-starter-tomcat'
         *     }
         * </pre>
         */
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ConditionalOnMyClass.class.getName());
        assert annotationAttributes != null;
        return ClassUtils.isPresent(
                annotationAttributes.get("value").toString(),
                context.getClassLoader()
        );
    }

}
