package com.example.realq.domain.station.repository;

import com.example.realq.domain.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}
