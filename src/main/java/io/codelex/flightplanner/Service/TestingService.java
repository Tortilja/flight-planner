package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.Repository.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class TestingService {
    private final FlightRepository flightRepository;

    public TestingService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    public void clearFlights(){
        flightRepository.clearFlights();
    }
}
