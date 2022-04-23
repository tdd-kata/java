package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

public class Normal implements Speed {
    @Override
    public String blueLight() {
        return "무 궁 화    꽃   이";
    }

    @Override
    public String redLight() {
        return "피 었 습 니  다.";
    }
}
