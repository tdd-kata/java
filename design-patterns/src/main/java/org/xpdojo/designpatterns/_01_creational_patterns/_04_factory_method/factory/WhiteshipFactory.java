package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.factory;

import org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.ship.Ship;
import org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.ship.Whiteship;

public class WhiteshipFactory extends DefaultShipFactory {

    @Override
    public Ship createShip() {
        return new Whiteship();
    }
}
