package com.example.realq.domain.average.region.controller;

import com.example.realq.domain.average.region.SearchConditionEnum;
import com.example.realq.domain.average.region.dto.response.AverageRegionResponse;
import com.example.realq.domain.average.region.service.AverageRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class AverageRegionController {

    private final AverageRegionService averageRegionService;

    @GetMapping
    public ResponseEntity<AverageRegionResponse> getByRegion(@RequestParam("region") String region, @RequestParam("period")SearchConditionEnum period) {
        return new ResponseEntity<>(averageRegionService.getByRegionAndPeriod(region, period), HttpStatus.OK);
    }
}
