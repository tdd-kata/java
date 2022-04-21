package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory;

import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.Button;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.Checkbox;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.MacButton;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.MacCheckbox;

public class MacFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }
}
