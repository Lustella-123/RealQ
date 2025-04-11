package com.example.realq.domain.average.district.service;

import com.example.realq.domain.average.district.SearchConditionEnum;
import com.example.realq.domain.average.district.client.AverageDistrictApiClient;
import com.example.realq.domain.average.district.dto.response.AverageDistrictItem;
import com.example.realq.domain.average.district.dto.response.AverageDistrictResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AverageDistrictService {

    private final AverageDistrictApiClient apiClient;

    public AverageDistrictResponse getByRegionAndPeriod(String region, SearchConditionEnum period) {
        AverageDistrictItem[] items = apiClient.getData(region, period);

        if (items.length == 0) {
            throw new GlobalException(ErrorCode.DISTRICT_API_ERROR);
        }

        AverageDistrictItem item = items[0];

        return new AverageDistrictResponse(
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
