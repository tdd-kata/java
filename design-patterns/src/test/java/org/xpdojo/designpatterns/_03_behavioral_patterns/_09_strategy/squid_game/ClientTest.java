package org.xpdojo.designpatterns._03_behavioral_patterns._09_strategy.squid_game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientTest {

    @Test
    void sut_red_light_blue_light() {
        BlueLightRedLight game = new BlueLightRedLight();

        String normal = game.blueLight(new Normal());
        assertThat(normal).isEqualTo("무 궁 화    꽃   이");

        String fastest = game.redLight(new Fastest());
        assertThat(fastest).isEqualTo("피어씀다.");

        String runtime = game.blueLight(new Speed() {
            @Override
            public String blueLight() {
                return "blue light";
            }

            @Override
            public String redLight() {
                return "red light";
            }
        });
        assertThat(runtime).isEqualTo("blue light");
    }

}
