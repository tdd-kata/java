package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShipFactoryTest {

    @Test
    void sut_whiteship_factory() {
        WhiteshipFactory whiteshipFactory = new WhiteshipFactory();
        Ship whiteship = order(whiteshipFactory, "whiteship", "imcxsu@gmail.com");

        assertThat(whiteship.getName()).isEqualTo("whiteship");
        assertThat(whiteship.getColor()).isEqualTo("white");
    }

    @Test
    void sut_blackship_factory() {
        BlackshipFactory blackshipFactory = new BlackshipFactory();
        Ship blackship = order(blackshipFactory, "blackship", "imcxsu@gmail.com");

        assertThat(blackship.getName()).isEqualTo("blackship");
        assertThat(blackship.getColor()).isEqualTo("black");
    }

    private Ship order(ShipFactory shipFactory, String name, String email) {
        return shipFactory.orderShip(name, email);
    }
}
