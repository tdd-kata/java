package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.factory;

import org.xpdojo.designpatterns._01_creational_patterns._04_factory_method.ship.Ship;

public abstract class DefaultShipFactory implements ShipFactory {

    @Override
    public void sendEmailTo(String email, Ship ship) {
        System.out.println(ship.getName() + " 다 만들었습니다.");
    }

}
