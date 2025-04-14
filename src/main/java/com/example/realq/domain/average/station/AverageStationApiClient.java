package com.example.realq.domain.average.station;

import com.example.realq.domain.realtime.region.client.RealtimeRegionApiClient;
import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionItem;
import com.example.realq.domain.realtime.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AverageStationApiClient {

    private final RealtimeRegionApiClient realtimeRegionApiClient;
    private final RegionRepository regionRepository;

    public List<AverageStation> getStationData() {
        log.info("메서드 실행: getStationData");

        List<String> regionNameList = regionRepository.findAllRegionNames();
        List<AverageStation> averageStationList = new ArrayList<>();

        regionNameList.forEach(region -> {
                    List<RealtimeRegionItem> realtimeRegionItemList = realtimeRegionApiClient.getRegionData(region);

                    realtimeRegionItemList.forEach(
                            realtimeRegionItem -> {
                                AverageStation averageStation = AverageStation.toEntity(
                                        realtimeRegionItem.stationName(),
                                        realtimeRegionItem.pm10Value(),
                                        realtimeRegionItem.pm25Value()
                                );

                                averageStationList.add(averageStation);

                            });
                }
        );
        return averageStationList;

    }
}
