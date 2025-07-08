package com.thy.routeplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String city;

    @Column(name = "location_code", unique = true, nullable = false)
    private String locationCode;

    private double latitude;

    private double longitude;


    public Location() {
    }

    public Location(String name, String country, String city, String locationCode) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.locationCode = locationCode;
    }

}
