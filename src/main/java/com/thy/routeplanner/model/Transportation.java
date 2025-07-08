package com.thy.routeplanner.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "transportations")
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "origin_id", nullable = false)
    private Location origin;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Location destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransportationType type;


    @Column(name = "day_of_week")
    private String operatingDays;

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public void setOperatingDays(String operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String getOperatingDays() {
        return operatingDays;
    }


}
