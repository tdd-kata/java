package org.xpdojo.spring.beanlifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ComponentScanTest {

    @Test
    void basic_scan() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        applicationContext.registerBean("emptyBean", EmptyBean.class);

        ComponentScanConfig componentScanConfig = applicationContext.getBean("componentScanTest.ComponentScanConfig", ComponentScanConfig.class);
        EmptyBean emptyBean = applicationContext.getBean("emptyBean", EmptyBean.class);

        assertThat(componentScanConfig).isInstanceOf(ComponentScanConfig.class);
        assertThat(emptyBean).isInstanceOf(EmptyBean.class);
    }

    static class ComponentScanConfig {
        public ComponentScanConfig() {
            System.out.println("ComponentScanConfig initialized");
        }
    }

    static class EmptyBean {
        public EmptyBean() {
            System.out.println("EmptyBean initialized");
        }
    }
}
