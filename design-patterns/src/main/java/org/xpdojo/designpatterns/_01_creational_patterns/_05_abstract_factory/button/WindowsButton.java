package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button;

public class WindowsButton implements Button {
    @Override
    public String paint() {
        return "Windows button";
    }
}
