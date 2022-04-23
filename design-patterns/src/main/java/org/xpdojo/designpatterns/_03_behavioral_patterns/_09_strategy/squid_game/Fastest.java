package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

public class Fastest implements Speed {
    @Override
    public String blueLight() {
        return "무광꼬치";
    }

    @Override
    public String redLight() {
        return "피어씀다.";
    }
}
