package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory;

import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.Button;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.Checkbox;

public interface GUIFactory {

    Button createButton();

    Checkbox createCheckbox();

}
