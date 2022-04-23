package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

public class Client {

    public static void main(String[] args) {
        BlueLightRedLight game = new BlueLightRedLight();
        System.out.println(game.blueLight(new Normal()));
        System.out.println(game.blueLight(new Fastest()));
        String speed = game.blueLight(new Speed() {
            @Override
            public String blueLight() {
                return "blue light";
            }

            @Override
            public String redLight() {
                return "red light";
            }
        });
        System.out.println(speed);
    }
}
