package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.Model.Flight;
import io.codelex.flightplanner.Repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
public class AdminService {
    private final FlightRepository flightRepository;
    private int nextId = 1;
    public AdminService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    public Flight fetchFlight(int id){
        Flight flight = flightRepository.getFlightById(id);
        if(flight == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with the specified ID does not exist.");
        }
            return flight;
    }
    public void addFlight(Flight flight) {
        validateAirportsAndDates(flight);
        flight.setId(nextId++);
        if (flightRepository.getFlights().contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists.");
        }
        flightRepository.addFlight(flight);
    }
    private void validateAirportsAndDates(Flight flight) {
        validateAirports(flight.getFrom(), flight.getTo());
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())
                || flight.getDepartureTime().isEqual(flight.getArrivalTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: Wrong airports date specified.");
        }
    }
    public void validateAirports(Object airportFrom, Object airportTo) {
        if (airportTo.equals(airportFrom)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: Same airports specified.");
        }
    }
    public void deleteFlightById(int id){
        flightRepository.deleteFlightById(id);
    }



}
