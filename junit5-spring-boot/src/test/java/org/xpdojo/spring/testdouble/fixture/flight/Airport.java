package org.xpdojo.spring.testdouble.fixture.flight;

import java.math.BigDecimal;

public class Airport {
    private BigDecimal id;
    private String code;
    private String name;
    private String nearByCity;

    public Airport(BigDecimal id, String code, String name, String nearByCity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.nearByCity = nearByCity;
    }

    public BigDecimal getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
