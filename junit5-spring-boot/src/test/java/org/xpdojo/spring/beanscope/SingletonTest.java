package org.xpdojo.spring.beanscope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

class SingletonTest {

    @Test
    @DisplayName("Singleton bean should be the same")
    void should_find_singleton_bean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Singleton.class);
        Singleton singletonBean1 = context.getBean(Singleton.class); // Singleton bean is created
        Singleton singletonBean2 = context.getBean(Singleton.class);

        assertThat(singletonBean1)
                .isEqualTo(singletonBean2) // 동등성 (equals)
                .isSameAs(singletonBean2) // 동일성 (identity)
        ;

        context.close(); // Singleton bean is destroyed
    }

    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    static class Singleton {

        private static Singleton instance;

        private Singleton() {
        }

        /**
         * ApplicationContext가 이 메서드를 사용하진 않는다.
         *
         * @return Singleton instance
         */
        public static Singleton getInstance() {
            if (instance == null) {
                instance = new Singleton();
            }
            return instance;
        }

        @PostConstruct
        public void init() {
            System.out.println("Singleton bean is created");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Singleton bean is destroyed");
        }
    }
}
