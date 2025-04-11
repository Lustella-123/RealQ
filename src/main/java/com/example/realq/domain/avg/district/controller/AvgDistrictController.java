package com.example.realq.domain.avg.district.controller;

import com.example.realq.domain.avg.district.SearchConditionEnum;
import com.example.realq.domain.avg.district.dto.response.AvgDistrictResponse;
import com.example.realq.domain.avg.district.service.AvgDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/district")
@RequiredArgsConstructor
public class AvgDistrictController {

    private final AvgDistrictService avgDistrictService;

    @GetMapping
    public ResponseEntity<AvgDistrictResponse> getByRegion(@RequestParam("region") String region, @RequestParam("period")SearchConditionEnum period) {
        return new ResponseEntity<>(avgDistrictService.getByRegionAndPeriod(region, period), HttpStatus.OK);
    }
}
