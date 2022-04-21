package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.checkbox;

public class WindowsCheckbox implements Checkbox {
    @Override
    public boolean isChecked() {
        return true;
    }
}
