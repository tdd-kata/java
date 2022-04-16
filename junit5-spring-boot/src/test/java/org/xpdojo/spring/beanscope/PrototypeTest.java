package org.xpdojo.spring.beanscope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

class PrototypeTest {

    @Test
    @DisplayName("Prototype bean should be created only once")
    void should_find_prototype_bean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Prototype.class);
        Prototype prototypeBean1 = context.getBean(Prototype.class); // Prototype bean is created
        Prototype prototypeBean2 = context.getBean(Prototype.class); // Prototype bean is created

        assertThat(prototypeBean1)
                .isNotEqualTo(prototypeBean2) // 동등성 (equals)
                .isNotSameAs(prototypeBean2) // 동일성 (identity)
        ;

        // 스프링 컨테이너에서 관리하지 않기 때문에 PreDestroy 적용되지 않는다.
        context.close();
    }

    /**
     * @see Scope#scopeName()
     */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    static class Prototype {

        private static Prototype instance;

        private Prototype() {
        }

        /**
         * ApplicationContext가 이 메서드를 사용하진 않는다.
         *
         * @return Prototype instance
         */
        public static Prototype getInstance() {
            return new Prototype();
        }

        @PostConstruct
        public void init() {
            System.out.println("Prototype bean is created");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("Prototype bean is destroyed");
        }
    }
}
