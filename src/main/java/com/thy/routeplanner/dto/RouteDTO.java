package com.thy.routeplanner.dto;

import java.util.List;

public class RouteDTO {
    private List<SegmentDTO> segments;

    public RouteDTO() {
    }

    public RouteDTO(List<SegmentDTO> segments) {
        this.segments = segments;
    }

    public List<SegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }
}
