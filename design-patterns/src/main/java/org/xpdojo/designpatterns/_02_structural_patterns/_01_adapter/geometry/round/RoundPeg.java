package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.round;

public class RoundPeg {
    private double radius;

    public RoundPeg() {
    }

    public RoundPeg(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
}
