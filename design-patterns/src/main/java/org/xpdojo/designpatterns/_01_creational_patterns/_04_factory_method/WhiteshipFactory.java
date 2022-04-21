package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

public class WhiteshipFactory extends DefaultShipFactory {

    @Override
    public Ship createShip() {
        return new Whiteship();
    }
}
