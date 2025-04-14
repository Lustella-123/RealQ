package com.example.realq.domain.client.station;

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

    public List<RealtimeRegionItem> getData() {
        List<String> regionNameList = regionRepository.findAllRegionNames();

        List<RealtimeRegionItem> realtimeRegionItemList = new ArrayList<>();

        for (String region : regionNameList) {
            List<RealtimeRegionItem> itemList = realtimeRegionApiClient.getRegionData(region);
            realtimeRegionItemList.addAll(itemList);
        }

        return realtimeRegionItemList;
    }
}