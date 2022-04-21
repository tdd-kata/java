package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring;

import org.springframework.beans.factory.FactoryBean;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.Button;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.WindowsButton;

public class ButtonFactoryBean implements FactoryBean<Button> {

    @Override
    public Button getObject() throws Exception {
        return new WindowsButton();
    }

    @Override
    public Class<?> getObjectType() {
        return Button.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
