package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.factory;

import org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.ship.Blackship;
import org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.ship.Ship;

public class BlackshipFactory extends DefaultShipFactory {
    @Override
    public Ship createShip() {
        return new Blackship();
    }
}
