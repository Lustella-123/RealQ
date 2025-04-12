package com.example.realq.domain.realtime.region.repository;

import com.example.realq.domain.realtime.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query("SELECT r.name FROM Region r")
    List<String> findAllRegionNames();
}