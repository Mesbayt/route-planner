package com.thy.routeplanner.dto;

import com.thy.routeplanner.model.TransportationType;
import java.util.List;

public class SegmentDTO {
    private TransportationType type;
    private String from;
    private String to;
    private String fromCity;
    private String toCity;
    private String operatingDays;

    // Getters & Setters

    public TransportationType getType() {
        return type;
    }

    public void setType(TransportationType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(String operatingDays) {
        this.operatingDays = operatingDays;
    }
}
