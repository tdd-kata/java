package org.xpdojo.spring.testdouble.fixture;

/**
 * xUnit Test Pattern
 */
public class FlightDto {
    private int flightNumber;
    private String from;
    private String to;

    public FlightDto(int flightNumber, String from, String to) {
        this.flightNumber = flightNumber;
        this.from = from;
        this.to = to;
    }

    public int getFlightNumber() {
        return flightNumber;
    }
}
