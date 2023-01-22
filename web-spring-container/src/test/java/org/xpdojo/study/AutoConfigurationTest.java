package org.xpdojo.study;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

class AutoConfigurationTest {

    @Test
    void proxyBeanMethods() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyConfig.class);
        context.refresh();

        Bean1 bean1 = context.getBean(Bean1.class);
        Bean2 bean2 = context.getBean(Bean2.class);

        assertThat(bean1.common).isSameAs(bean2.common);
    }

    /**
     * proxyBeanMethods = false일 경우 Bean1, Bean2에서 Common을 주입받을 때마다 새로운 Common 객체를 생성한다.
     * proxyBeanMethods = true일 경우 Bean1, Bean2에서 Common을 주입받을 때마다 동일한 Common 객체를 사용한다.
     * 의존하는 객체 없이 그 자체로 독립적으로 사용할 수 있는 Bean은 proxyBeanMethods = false로 설정해서 사용할 수 있다.
     */
    // @Configuration(proxyBeanMethods = false)
    @Configuration(proxyBeanMethods = true)
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }


    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {
    }

}
