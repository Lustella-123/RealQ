package com.example.realq.domain.avg.district.service;

import com.example.realq.domain.avg.district.SearchConditionEnum;
import com.example.realq.domain.avg.district.client.AvgDistrictApiClient;
import com.example.realq.domain.avg.district.dto.response.AvgDistrictItem;
import com.example.realq.domain.avg.district.dto.response.AvgDistrictResponse;
import com.example.realq.global.error.ErrorCode;
import com.example.realq.global.error.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvgDistrictService {

    private final AvgDistrictApiClient apiClient;

    public AvgDistrictResponse getByRegionAndPeriod(String region, SearchConditionEnum period) {
        AvgDistrictItem[] items = apiClient.getData(region, period);

        if (items.length == 0) {
            throw new GlobalException(ErrorCode.DISTRICT_API_ERROR);
        }

        AvgDistrictItem item = items[0];

        return new AvgDistrictResponse(
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
