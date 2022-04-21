package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory;

public class WindowsButton implements Button {
    @Override
    public String paint() {
        return "Windows button";
    }
}
