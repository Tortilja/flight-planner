package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.Model.Airport;
import io.codelex.flightplanner.Request.SearchFlightsRequest;
import io.codelex.flightplanner.Repository.FlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.codelex.flightplanner.Model.Flight;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CustomerService {
    private final FlightRepository flightRepository;

    public CustomerService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    public List<Airport> searchAirport(String phrase) {
        String lowerCaseTrimmedPhrase = phrase.toLowerCase().trim();
        List<Airport> airports = new ArrayList<>();

        flightRepository.getFlights().stream()
                .flatMap(flight -> Stream.of(flight.getFrom(), flight.getTo()))
                .forEach(airport -> {
                    if (airport.getAirport().toLowerCase().contains(lowerCaseTrimmedPhrase)
                            || airport.getCity().toLowerCase().contains(lowerCaseTrimmedPhrase)
                            || airport.getCountry().toLowerCase().contains(lowerCaseTrimmedPhrase)) {
                        if (!airports.contains(airport)) {
                            airports.add(airport);
                        }
                    }
                });

        return airports;
    }
    public List<Flight> searchFlight(SearchFlightsRequest searchFlightsRequest) {
        if (searchFlightsRequest.getFrom().equals(searchFlightsRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Flight> flights = flightRepository.getFlights();

        Airport from = searchAirport(searchFlightsRequest.getFrom()).stream().findFirst().orElse(null);
        Airport to = searchAirport(searchFlightsRequest.getTo()).stream().findFirst().orElse(null);

        return flights.stream()
                .filter(f -> f.getFrom().equals(from) &&
                        f.getTo().equals(to) && f.getDepartureTime().toLocalDate()
                                .equals(searchFlightsRequest.getDepartureDate()))
                                .toList();
    }

    public Flight findFlightById(int id) {
        return flightRepository.getFlights().stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
