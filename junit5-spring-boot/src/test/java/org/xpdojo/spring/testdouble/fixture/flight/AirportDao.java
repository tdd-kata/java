package org.xpdojo.spring.testdouble.fixture.flight;

import java.math.BigDecimal;

public interface AirportDao {
    Airport createAirport(String airportCode, String name, String nearByCity);
    Airport getAirportById(BigDecimal airportId);
}
