package io.codelex.flightplanner.Controller;

import io.codelex.flightplanner.Model.Flight;
import io.codelex.flightplanner.Service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("flights/{id}")
    public ResponseEntity<Flight> fetchFlight(@PathVariable("id") int id) {
      Flight flight = adminService.fetchFlight(id);
      if (flight == null){
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(flight);
    }

    @PutMapping("/flights")
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody Flight flight) {
        this.adminService.addFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(flight);
    }

    @DeleteMapping("flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlight(@PathVariable int id) {
        adminService.deleteFlightById(id);
    }

}
