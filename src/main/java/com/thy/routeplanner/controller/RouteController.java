package com.thy.routeplanner.controller;

import com.thy.routeplanner.dto.RouteDTO;
import com.thy.routeplanner.model.Transportation;
import com.thy.routeplanner.service.RouteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<RouteDTO> findRoutes(
            @RequestParam String originCode,
            @RequestParam String destinationCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return routeService.findRoutes(originCode, destinationCode, date);
    }

}
