package com.java.Zhimdhen_POS.dailysalessummary.repository;

import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailySalesSummaryRepository extends JpaRepository<DailySalesSummary, Long> {
    List<DailySalesSummary> findBySaleDate(LocalDate date);
}
