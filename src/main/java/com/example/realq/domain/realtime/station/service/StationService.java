package com.example.realq.domain.realtime.station.service;

import com.example.realq.domain.realtime.station.client.StationApiClient;
import com.example.realq.domain.realtime.station.dto.response.StationItem;
import com.example.realq.domain.realtime.station.dto.response.StationResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationApiClient apiClient;

    public StationResponse getByStation(String station) {
        StationItem[] items = apiClient.getStationData(station);

        if (items.length == 0) {
            throw new GlobalException(ErrorCode.AIR_QUALITY_NOT_AVAILABLE);
        }

        StationItem item = items[0];

        return new StationResponse(
                item.stationName(),
                item.dataTime(),
                Double.parseDouble(item.pm10Value()),
                Double.parseDouble(item.pm25Value()),
                Double.parseDouble(item.o3Value()),
                Double.parseDouble(item.no2Value()),
                Double.parseDouble(item.coValue()),
                Double.parseDouble(item.so2Value())
        );
    }
}