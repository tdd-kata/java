package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory;

import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.button.Button;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox.Checkbox;
import org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.factory.GUIFactory;

public class Application {
    private final GUIFactory factory;
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public String paint() {
        return button.paint();
    }

    public boolean isChecked() {
        return checkbox.isChecked();
    }
}
