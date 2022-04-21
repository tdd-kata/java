package org.xpdojo.designpatterns._02_structural_patterns._01_adapter.geometry.round;

/**
 * RoundHoles are compatible with RoundPegs.
 */
public class RoundHole {
    private final double radius;

    public RoundHole(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public boolean fits(RoundPeg peg) {
        return (peg.getRadius() <= radius);
    }
}
