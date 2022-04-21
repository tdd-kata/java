package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring.Car;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring.CarFactoryBeanConfig;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryBeanTest {

    @Test
    void sut_factory_bean_from_xml_configuration() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

        String hello = applicationContext.getBean("hello", String.class);
        assertThat(hello).isEqualTo("hi");

        Button button = applicationContext.getBean("button", Button.class);
        assertThat(button.paint()).isEqualTo("Windows button");
    }

    @Test
    void sut_factory_bean_from_java_configuration() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarFactoryBeanConfig.class);
        Car car = applicationContext.getBean(Car.class);

        assertThat(car.getManufacturer()).isEqualTo("Tesla");
        assertThat(car.getModel()).isEqualTo("Model S");
    }

}
