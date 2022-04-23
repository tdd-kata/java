package org.xpdojo.designpatterns._02_structural_patterns._06_flyweight;

public class Character {

    private char value;

    private String color;

    private Font font;

    public Character(char value, String color, Font font) {
        this.value = value;
        this.color = color;
        this.font = font;
    }

    public Font getFont() {
        return font;
    }
}
