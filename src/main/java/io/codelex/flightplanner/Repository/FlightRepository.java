package io.codelex.flightplanner.Repository;

import io.codelex.flightplanner.Model.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {
    private List<Flight> flightList = new ArrayList<>();
    private Long nextFlightId = 1L;

    public void clearFlights(){
        flightList.clear();
    }
    public List<Flight> getFlights() {
        return this.flightList;
    }
    public Flight getFlightById(int id) {
        return flightList.stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean addFlight(Flight flight) {
        synchronized (flightList) {
            return this.flightList.add(flight);
        }
    }

    public void deleteFlightById(int id) {
        synchronized (flightList) {
            flightList.removeIf(flight -> flight.getId() == id);
        }
    }

}
