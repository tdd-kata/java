package org.xpdojo.designpatterns._01_creational_patterns._05_abstract_factory.spring;

public class Car {

    private final String manufacturer;
    private final String model;

    public Car(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }
}
