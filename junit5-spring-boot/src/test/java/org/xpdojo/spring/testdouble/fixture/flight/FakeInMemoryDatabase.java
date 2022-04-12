package org.xpdojo.spring.testdouble.fixture.flight;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeInMemoryDatabase implements AirportDao {

    private final Map<BigDecimal, Airport> airports = new ConcurrentHashMap<>();
    private BigDecimal nextAirportId = new BigDecimal(1);

    @Override
    public Airport createAirport(String airportCode, String name, String nearByCity) {
        Airport airport = new Airport(nextAirportId, airportCode, name, nearByCity);
        nextAirportId = getNextAirportId();

        airports.put(airport.getId(), airport);
        return airport;
    }

    @Override
    public Airport getAirportById(BigDecimal airportId) {
        return airports.get(airportId);
    }

    private BigDecimal getNextAirportId() {
        return nextAirportId.add(new BigDecimal(1));
    }
}
