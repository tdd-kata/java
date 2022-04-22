package org.xpdojo.designpatterns._02_structural_patterns._03_composite;

public class Item implements Component {

    private String name;

    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }
}
