package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape;

import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.device.Device;

public interface Shape {

    String accept(Device device);

}
