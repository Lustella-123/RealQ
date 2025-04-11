package com.example.realq.domain.realtime.station.service;

import com.example.realq.domain.realtime.station.client.RealtimeStationApiClient;
import com.example.realq.domain.realtime.station.dto.response.RealtimeStationItem;
import com.example.realq.domain.realtime.station.dto.response.RealtimeStationResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealtimeStationService {

    private final RealtimeStationApiClient apiClient;

    public RealtimeStationResponse getByStation(String station) {
        RealtimeStationItem[] items = apiClient.getStationData(station);

        if (items.length == 0) {
            throw new GlobalException(ErrorCode.AIR_QUALITY_NOT_AVAILABLE);
        }

        RealtimeStationItem item = items[0];

        return new RealtimeStationResponse(
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