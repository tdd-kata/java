package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * xUnit Test Pattern
 */
public class FlightManagementFacadeImpl implements FlightManagementFacade {
    private Map<Integer, FlightDto> flights;
    private AuditLog auditLog;

    public FlightManagementFacadeImpl() {
        this.flights = new HashMap<>();
    }

    public void setAuditLog(AuditLog auditLog) {
        this.auditLog = auditLog;
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
}
