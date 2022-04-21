package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring;

import org.springframework.beans.factory.FactoryBean;

public class CarFactory implements FactoryBean<Car> {

    @Override
    public Car getObject() throws Exception {
        return new Car("Tesla", "Model S");
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
