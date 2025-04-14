package com.example.realq.domain.slack.repository;

import com.example.realq.domain.slack.entity.AverageRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<AverageRegion, Long> {
}
