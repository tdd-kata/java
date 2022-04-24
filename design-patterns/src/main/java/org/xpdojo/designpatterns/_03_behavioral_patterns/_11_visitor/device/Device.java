package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.device;

import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Circle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Rectangle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Triangle;

public interface Device {
    String print(Circle circle);

    String print(Rectangle rectangle);

    String print(Triangle triangle);
}
