package org.xpdojo.designpatterns._02_structural_patterns._03_composite;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComponentTest {

    @Test
    void sut_composite() {
        // Component == Component
        // Bag == Composite
        // Item == Leaf
        Item sword = new Item("칼", 450);
        Item shield = new Item("방패", 50);

        Bag bag = new Bag();
        bag.add(sword);
        bag.add(shield);

        assertThat(getComponentPrice(bag))
                .isEqualTo(sword.getPrice() + shield.getPrice());
    }

    private int getComponentPrice(Component component) {
        return component.getPrice();
    }

}
