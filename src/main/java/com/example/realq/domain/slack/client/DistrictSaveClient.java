package com.example.realq.domain.slack.client;

import com.example.realq.domain.average.district.SearchConditionEnum;
import com.example.realq.domain.average.district.client.AverageDistrictApiClient;
import com.example.realq.domain.average.district.dto.response.AverageDistrictItem;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import com.example.realq.domain.slack.entity.AverageDistrict;
import com.example.realq.domain.slack.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DistrictSaveClient {

    private final AverageDistrictApiClient apiClient;
    private final DistrictRepository districtSaveRepository;
    private final RegionRepository regionSaveRepository;

    @Scheduled(cron = "0 */1 * * * *")
    public void saveAverageDistrict() {

        districtSaveRepository.deleteAll();

        List<String> regionNameList = regionSaveRepository.findAllRegionNames();
        List<AverageDistrict> averageDistrictList = new ArrayList<>();

        regionNameList.forEach(region -> {
            AverageDistrictItem[] data = apiClient.getData(region, SearchConditionEnum.HOUR);
            AverageDistrictItem averageDistrictItem = data[0];

            log.info("name: {}", averageDistrictItem.stationName());

            AverageDistrict averageDistrict = AverageDistrict.toEntity(
                    region,
                    averageDistrictItem.pm10Value(),
                    averageDistrictItem.pm25Value()
            );

            averageDistrictList.add(averageDistrict);

        });

        districtSaveRepository.saveAll(averageDistrictList);
    }
}