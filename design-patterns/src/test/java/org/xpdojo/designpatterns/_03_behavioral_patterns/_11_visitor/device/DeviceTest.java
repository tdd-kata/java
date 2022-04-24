package org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.device;

import org.junit.jupiter.api.Test;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Circle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Rectangle;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Shape;
import org.xpdojo.designpatterns._03_behavioral_patterns._11_visitor.shape.Triangle;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceTest {

    @Test
    void sut_rectangle() {
        Shape rectangle = new Rectangle();
        Device pad = new Pad();
        Device phone = new Phone();
        assertThat(rectangle.accept(pad)).isEqualTo("Print Rectangle to Pad");
        assertThat(rectangle.accept(phone)).isEqualTo("Print Rectangle to Phone");
    }

    @Test
    void sut_circle() {
        Shape circle = new Circle();
        Device watch = new Watch();
        Device phone = new Phone();
        assertThat(circle.accept(watch)).isEqualTo("Print Circle to Watch");
        assertThat(circle.accept(phone)).isEqualTo("Print Circle to Phone");
    }

    @Test
    void sut_triangle() {
        Shape triangle = new Triangle();
        Device watch = new Watch();
        Device pad = new Pad();
        assertThat(triangle.accept(watch)).isEqualTo("Print Triangle to Watch");
        assertThat(triangle.accept(pad)).isEqualTo("Print Triangle to Pad");
    }

}
