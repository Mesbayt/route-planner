package com.thy.routeplanner.controller;

import com.thy.routeplanner.model.Transportation;
import com.thy.routeplanner.model.Location;
import com.thy.routeplanner.repository.LocationRepository;
import com.thy.routeplanner.repository.TransportationRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(name = "transportation-controller")
@RestController
@RequestMapping("/api/transportations")
public class TransportationController {

    private final TransportationRepository transportationRepository;
    private final LocationRepository locationRepository;

    public TransportationController(TransportationRepository transportationRepository, LocationRepository locationRepository) {
        this.transportationRepository = transportationRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public List<Transportation> getAll() {
        return transportationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportation> getById(@PathVariable Long id) {
        Optional<Transportation> result = transportationRepository.findById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Transportation transportation) {
        if (transportation.getOrigin() == null || transportation.getDestination() == null) {
            return ResponseEntity.badRequest().body("Origin and destination are required");
        }

        Optional<Location> origin = locationRepository.findById(transportation.getOrigin().getId());
        Optional<Location> destination = locationRepository.findById(transportation.getDestination().getId());

        if (origin.isEmpty() || destination.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid origin or destination");
        }

        transportation.setOrigin(origin.get());
        transportation.setDestination(destination.get());

        return ResponseEntity.ok(transportationRepository.save(transportation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!transportationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        transportationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
