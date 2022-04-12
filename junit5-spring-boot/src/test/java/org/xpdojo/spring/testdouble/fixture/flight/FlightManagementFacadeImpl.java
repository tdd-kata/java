package org.xpdojo.spring.testdouble.fixture.flight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * xUnit Test Pattern
 */
public class FlightManagementFacadeImpl implements FlightManagementFacade {
    private Map<Integer, FlightDto> flights;
    private AirportDao dao;
    private AuditLog auditLog;

    public FlightManagementFacadeImpl() {
        this.flights = new ConcurrentHashMap<>();
    }

    public void setAuditLog(AuditLog auditLog) {
        this.auditLog = auditLog;
    }

    public void setDao(AirportDao dao) {
        this.dao = dao;
    }

    @Override
    public void registerFlight(FlightDto flight) {
        if (flightExists(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Flight already exists");
        }
        flights.put(flight.getFlightNumber(), flight);
    }

    @Override
    public void removeFlight(int flightNumber) {
        flights.remove(flightNumber);
        auditLog.logMessage(LocalDate.now(), "test", ActionCode.REMOVE_FLIGHT_ACTION_CODE, "removeFlight");
    }

    @Override
    public boolean flightExists(int flightNumber) {
        return flights.containsKey(flightNumber);
    }

    @Override
    public Airport createAirport(String airportCode, String name, String nearByCity) {
        return dao.createAirport(airportCode, name, nearByCity);
    }

    @Override
    public Airport getAirport(BigDecimal airportId) {
        return dao.getAirportById(airportId);
    }
}
