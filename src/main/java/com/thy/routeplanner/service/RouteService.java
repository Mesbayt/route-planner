package com.thy.routeplanner.service;

import com.thy.routeplanner.model.Location;
import com.thy.routeplanner.model.Transportation;
import com.thy.routeplanner.model.TransportationType;
import com.thy.routeplanner.repository.LocationRepository;
import com.thy.routeplanner.repository.TransportationRepository;
import org.springframework.stereotype.Service;

import com.thy.routeplanner.dto.RouteDTO;
import com.thy.routeplanner.dto.SegmentDTO;

import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.*;

@Service
public class RouteService {

    private final LocationRepository locationRepository;
    private final TransportationRepository transportationRepository;

    public RouteService(LocationRepository locationRepository,
                        TransportationRepository transportationRepository) {
        this.locationRepository = locationRepository;
        this.transportationRepository = transportationRepository;
    }
    @Cacheable(value = "routes", key = "#originCode + '_' + #destinationCode + '_' + #date")
    public List<RouteDTO> findRoutes(String originCode, String destinationCode, LocalDate date) {
        // Gün değerini al (1-7 formatında: Pazartesi = 1)
        int day = date.getDayOfWeek().getValue();
        String dayString = String.valueOf(day);


        Location origin = locationRepository.findAll().stream()
                .filter(l -> l.getLocationCode().equalsIgnoreCase(originCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Origin not found"));

        Location destination = locationRepository.findAll().stream()
                .filter(l -> l.getLocationCode().equalsIgnoreCase(destinationCode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        // Tüm taşımaları al, sadece o gün çalışanları filtrele
        List<Transportation> all = transportationRepository.findAll();
        List<Transportation> available = all.stream()
                .filter(t -> t.getOperatingDays().contains(dayString))
                .toList();

        List<List<Transportation>> validRoutes = new ArrayList<>();

        for (Transportation t1 : available) {
            if (!t1.getType().equals(TransportationType.FLIGHT)) continue;

            // Direkt uçuş
            if (t1.getOrigin().equals(origin) && t1.getDestination().equals(destination)) {
                validRoutes.add(List.of(t1));
            }

            // Ön transfer + uçuş
            for (Transportation before : available) {
                if (before.getType() == TransportationType.FLIGHT) continue;
                if (!before.getOrigin().equals(origin)) continue;
                if (!before.getDestination().equals(t1.getOrigin())) continue;
                if (!t1.getDestination().equals(destination)) continue;

                validRoutes.add(List.of(before, t1));
            }

            // Uçuş + sonrası transfer
            for (Transportation after : available) {
                if (after.getType() == TransportationType.FLIGHT) continue;
                if (!t1.getOrigin().equals(origin)) continue;
                if (!t1.getDestination().equals(after.getOrigin())) continue;
                if (!after.getDestination().equals(destination)) continue;

                validRoutes.add(List.of(t1, after));
            }

            // Üçlü kombinasyon (ön → uçuş → sonrası)
            for (Transportation before : available) {
                if (before.getType() == TransportationType.FLIGHT) continue;
                if (!before.getOrigin().equals(origin)) continue;
                if (!before.getDestination().equals(t1.getOrigin())) continue;

                for (Transportation after : available) {
                    if (after.getType() == TransportationType.FLIGHT) continue;
                    if (!t1.getDestination().equals(after.getOrigin())) continue;
                    if (!after.getDestination().equals(destination)) continue;

                    validRoutes.add(List.of(before, t1, after));
                }
            }
        }


        List<RouteDTO> result = new ArrayList<>();
        for (List<Transportation> route : validRoutes) {
            List<SegmentDTO> segments = new ArrayList<>();
            for (Transportation t : route) {
                SegmentDTO dto = new SegmentDTO();
                dto.setType(t.getType());
                dto.setFrom(t.getOrigin().getLocationCode());
                dto.setTo(t.getDestination().getLocationCode());
                dto.setFromCity(t.getOrigin().getCity());
                dto.setToCity(t.getDestination().getCity());
                dto.setOperatingDays(t.getOperatingDays());
                dto.setFromLat(t.getOrigin().getLatitude());
                dto.setFromLng(t.getOrigin().getLongitude());
                dto.setToLat(t.getDestination().getLatitude());
                dto.setToLng(t.getDestination().getLongitude());
                segments.add(dto);
            }
            result.add(new RouteDTO(segments));
        }



        return result;
    }
}
