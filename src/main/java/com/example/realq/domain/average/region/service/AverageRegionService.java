package com.example.realq.domain.average.region.service;

import com.example.realq.domain.average.region.SearchConditionEnum;
import com.example.realq.domain.average.region.dto.response.AverageRegionItem;
import com.example.realq.domain.average.region.dto.response.AverageRegionResponse;
import com.example.realq.domain.client.region.AverageRegionApiClient;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AverageRegionService {

    private final AverageRegionApiClient apiClient;

    public AverageRegionResponse getByRegionAndPeriod(String region, SearchConditionEnum period) {
        AverageRegionItem[] items = apiClient.getData(region, period);

        if (items.length == 0) {
            throw new GlobalException(ErrorCode.REALTIME_AVERAGE_API_ERROR);
        }

        AverageRegionItem item = items[0];

        return new AverageRegionResponse(
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
