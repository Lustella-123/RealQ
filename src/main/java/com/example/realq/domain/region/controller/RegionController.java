package com.example.realq.domain.region.controller;

import com.example.realq.domain.region.dto.response.RegionResponse;
import com.example.realq.domain.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<RegionResponse> getByRegion(@RequestParam("region") String region) {
        return new ResponseEntity<>(regionService.getByRegion(region), HttpStatus.OK);
    }
}
