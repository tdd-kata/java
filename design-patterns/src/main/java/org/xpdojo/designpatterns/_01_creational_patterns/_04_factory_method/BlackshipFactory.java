package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

public class BlackshipFactory extends DefaultShipFactory {
    @Override
    public Ship createShip() {
        return new Blackship();
    }
}
