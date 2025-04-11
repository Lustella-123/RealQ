package com.example.realq.domain.realtime.station.controller;

import com.example.realq.domain.realtime.station.dto.response.StationResponse;
import com.example.realq.domain.realtime.station.service.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping
    public ResponseEntity<StationResponse> getByStation(@RequestParam("station") String station) {
        return new ResponseEntity<>(stationService.getByStation(station), HttpStatus.OK);
    }
}
