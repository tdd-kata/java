package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory;

import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.Button;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.Checkbox;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.WindowsButton;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.WindowsCheckbox;

public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
