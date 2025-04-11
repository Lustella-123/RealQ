package com.example.realq.domain.realtime.region.dto.response;

import java.util.List;

public record RealtimeRegionBody(
        List<RealtimeRegionItem> items
) {}
