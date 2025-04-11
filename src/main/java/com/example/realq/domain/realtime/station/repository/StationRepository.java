package com.example.realq.domain.realtime.station.repository;

import com.example.realq.domain.realtime.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
