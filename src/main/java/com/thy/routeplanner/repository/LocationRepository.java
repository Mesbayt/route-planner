package com.thy.routeplanner.repository;

import com.thy.routeplanner.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // Ek sorgular gerekiyorsa burada tanımlanır.
    boolean existsByLocationCode(String locationCode);
}
