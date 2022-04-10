package org.xpdojo.spring.testdouble.fixture;

public interface FlightManagementFacade {
    void registerFlight(FlightDto flight);
    void removeFlight(int flightNumber);
    boolean flightExists(int flightNumber);
}
