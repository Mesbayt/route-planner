package com.thy.routeplanner.repository;

import com.thy.routeplanner.model.Transportation;
import com.thy.routeplanner.model.Location;
import com.thy.routeplanner.model.TransportationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    List<Transportation> findByOrigin(Location origin);

    List<Transportation> findByDestination(Location destination);

    List<Transportation> findByOriginAndDestination(Location origin, Location destination);

    List<Transportation> findByType(TransportationType type);
}
