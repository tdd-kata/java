package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {

    @Test
    void sut_bean_factory() {
        BeanFactory xmlFactory = new ClassPathXmlApplicationContext("config.xml");
        String hello = xmlFactory.getBean("hello", String.class);
        assertThat(hello).isEqualTo("hi");

        BeanFactory javaFactory = new AnnotationConfigApplicationContext(BeanFactoryConfig.class);
        String hi = javaFactory.getBean("hi", String.class);
        assertThat(hi).isEqualTo("hello");
    }
}
