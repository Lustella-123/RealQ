package com.example.realq.domain.client.station;

import com.example.realq.domain.average.station.AverageStation;
import com.example.realq.domain.realtime.region.dto.response.RealtimeRegionItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StationGetClient {

    private final AverageStationApiClient averageStationApiClient;

    public List<AverageStation> getAverageStation() {
        log.info("메서드 실행: getAverageStation");

        List<RealtimeRegionItem> realtimeRegionItemList = averageStationApiClient.getData();

        List<AverageStation> averageStationList = new ArrayList<>();

        realtimeRegionItemList.forEach(item -> {
            AverageStation averageStation = AverageStation.toEntity(
                    item.stationName(),
                    item.pm10Value(),
                    item.pm25Value()
            );

            averageStationList.add(averageStation);
        });

        return averageStationList;
    }
}
