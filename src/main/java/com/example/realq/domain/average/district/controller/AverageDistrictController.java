package com.example.realq.domain.average.district.controller;

import com.example.realq.domain.average.district.SearchConditionEnum;
import com.example.realq.domain.average.district.dto.response.AverageDistrictResponse;
import com.example.realq.domain.average.district.service.AverageDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class AverageDistrictController {

    private final AverageDistrictService avgDistrictService;

    @GetMapping
    public ResponseEntity<AverageDistrictResponse> getByRegion(@RequestParam("region") String region, @RequestParam("period")SearchConditionEnum period) {
        return new ResponseEntity<>(avgDistrictService.getByRegionAndPeriod(region, period), HttpStatus.OK);
    }
}
