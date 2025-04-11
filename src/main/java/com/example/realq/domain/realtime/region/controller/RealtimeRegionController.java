package com.example.realq.domain.realtime.region.controller;

import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionResponse;
import com.example.realq.domain.realtime.region.service.RealtimeRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RealtimeRegionController {

    private final RealtimeRegionService regionService;

    @GetMapping
    public ResponseEntity<List<RealtimeRegionResponse>> getByRegion(@RequestParam("region") String region) {
        return new ResponseEntity<>(regionService.getByRegion(region), HttpStatus.OK);
    }
}
