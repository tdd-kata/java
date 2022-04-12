package org.xpdojo.spring.testdouble.fixture.flight;

import java.math.BigDecimal;

/**
 * xUnit Test Pattern
 */
public interface FlightManagementFacade {
    void registerFlight(FlightDto flight);

    void removeFlight(int flightNumber);

    boolean flightExists(int flightNumber);

    Airport createAirport(String airportCode, String name, String nearByCity);

    Airport getAirport(BigDecimal airportId);
}
