package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

public class BlueLightRedLight {

    public String blueLight(Speed speed) {
        return speed.blueLight();
    }

    public String redLight(Speed speed) {
        return speed.redLight();
    }
}
