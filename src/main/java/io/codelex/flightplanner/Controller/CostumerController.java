package io.codelex.flightplanner.Controller;

import io.codelex.flightplanner.Model.Airport;
import io.codelex.flightplanner.Model.Flight;
import io.codelex.flightplanner.Response.PageResult;
import io.codelex.flightplanner.Request.SearchFlightsRequest;
import io.codelex.flightplanner.Service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController

@RequestMapping("/api")
public class CostumerController {
    private final CustomerService customerService;

    public CostumerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam String search){
        List<Airport> matchingAirports = customerService.searchAirport(search);
        return ResponseEntity.ok(matchingAirports);
    }
    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@RequestBody @Valid SearchFlightsRequest searchFlightsRequest) {
        List<Flight> foundFlights = customerService.searchFlight(searchFlightsRequest);
        return new PageResult<>(0, foundFlights.size(), foundFlights);
    }
    @GetMapping("/flights/{id}")
    public ResponseEntity<Flight> findFlightById(@PathVariable("id") int id) {
        Flight flight = customerService.findFlightById(id);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(flight);
    }

}
