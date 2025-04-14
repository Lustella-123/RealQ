package com.example.realq.domain.notification.district;

import com.example.realq.domain.average.district.SearchConditionEnum;
import com.example.realq.domain.average.district.client.AverageDistrictApiClient;
import com.example.realq.domain.average.district.dto.response.AverageDistrictItem;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import com.example.realq.domain.slack.entity.AverageRegion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegionGetClient {

    private final AverageDistrictApiClient apiClient;
    private final RegionRepository regionSaveRepository;

    public List<AverageRegion> getAverageRegion() {
        log.info("메서드 실행: getAverageRegion");

        List<String> regionNameList = regionSaveRepository.findAllRegionNames();
        List<AverageRegion> averageRegionList = new ArrayList<>();

        regionNameList.forEach(region -> {
            AverageDistrictItem[] data = apiClient.getData(region, SearchConditionEnum.HOUR);
            AverageDistrictItem averageDistrictItem = data[0];

            AverageRegion averageDistrict = AverageRegion.toEntity(
                    region,
                    averageDistrictItem.pm10Value(),
                    averageDistrictItem.pm25Value()
            );

            averageRegionList.add(averageDistrict);

        });

        return averageRegionList;
    }
}