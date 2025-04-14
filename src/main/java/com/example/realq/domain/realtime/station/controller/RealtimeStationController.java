package com.example.realq.domain.realtime.station.controller;

import com.example.realq.domain.realtime.station.dto.response.RealtimeStationResponse;
import com.example.realq.domain.realtime.station.service.RealtimeStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/realtime/stations")
@RequiredArgsConstructor
public class RealtimeStationController {

    private final RealtimeStationService stationService;

    @GetMapping
    public ResponseEntity<RealtimeStationResponse> getByStation(@RequestParam("station") String station) {
        return new ResponseEntity<>(stationService.getByStation(station), HttpStatus.OK);
    }
}
