package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.device;

import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Circle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Rectangle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Triangle;

public class Phone implements Device {

    @Override
    public String print(Circle circle) {
        return "Print Circle to Phone";
    }

    @Override
    public String print(Rectangle rectangle) {
        return "Print Rectangle to Phone";

    }

    @Override
    public String print(Triangle triangle) {
        return "Print Triangle to Phone";
    }
}
