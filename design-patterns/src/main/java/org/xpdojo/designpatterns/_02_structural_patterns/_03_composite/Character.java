package org.xpdojo.designpatterns._02_structural_patterns._03_composite;

public class Character implements Component {

    private Bag bag;

    @Override
    public int getPrice() {
        return bag.getPrice();
    }

}
