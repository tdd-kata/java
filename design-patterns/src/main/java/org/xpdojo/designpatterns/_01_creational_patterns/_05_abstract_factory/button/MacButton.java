package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button;

public class MacButton implements Button {
    @Override
    public String paint() {
        return "Mac button";
    }
}
