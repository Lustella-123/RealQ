package com.example.realq.domain.notification.district;

import com.example.realq.domain.average.region.SearchConditionEnum;
import com.example.realq.domain.average.region.client.AverageRegionApiClient;
import com.example.realq.domain.average.region.dto.response.AverageRegionItem;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import com.example.realq.domain.average.region.entity.AverageRegion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegionGetClient {

    private final AverageRegionApiClient apiClient;
    private final RegionRepository regionSaveRepository;

    public List<AverageRegion> getAverageRegion() {
        log.info("메서드 실행: getAverageRegion");

        List<String> regionNameList = regionSaveRepository.findAllRegionNames();
        List<AverageRegion> averageRegionList = new ArrayList<>();

        regionNameList.forEach(region -> {
            AverageRegionItem[] data = apiClient.getData(region, SearchConditionEnum.HOUR);
            AverageRegionItem averageDistrictItem = data[0];

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