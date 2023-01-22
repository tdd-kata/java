package org.xpdojo.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionalTest {

    @Test
    void conditional() {
        // config1
        ApplicationContextRunner contextRunner1 = new ApplicationContextRunner();
        contextRunner1.withUserConfiguration(Config1.class)
                .run(context1 -> {
                    assertThat(context1).hasSingleBean(MyBean.class);
                    assertThat(context1).hasSingleBean(Config1.class);
                });

        // config2
        ApplicationContextRunner contextRunner2 = new ApplicationContextRunner();
        contextRunner2.withUserConfiguration(Config2.class)
                .run(context2 -> {
                    assertThat(context2).doesNotHaveBean(MyBean.class);
                    assertThat(context2).doesNotHaveBean(Config1.class);
                });
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanCondition.class)
    @interface BooleanConditional {
        boolean value() default true;
    }

    @Configuration
    @BooleanConditional
    static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    @Configuration
    @BooleanConditional(false)
    static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }

    static class MyBean {
    }

    private static class BooleanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
            return (boolean) annotationAttributes.get("value");
        }
    }
}
