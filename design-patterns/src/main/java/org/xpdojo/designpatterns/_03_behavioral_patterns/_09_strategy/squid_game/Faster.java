package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

public class Faster implements Speed {
    @Override
    public String blueLight() {
        return "무궁화꽃이";
    }

    @Override
    public String redLight() {
        return "피었습니다.";
    }
}
