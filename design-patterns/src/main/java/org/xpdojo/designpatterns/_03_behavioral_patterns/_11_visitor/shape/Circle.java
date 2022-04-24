package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape;

import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.device.Device;

public class Circle implements Shape {

    @Override
    public String accept(Device device) {
        return device.print(this);
    }
}
