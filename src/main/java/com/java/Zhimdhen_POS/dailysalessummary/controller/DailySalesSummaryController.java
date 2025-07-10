package com.java.Zhimdhen_POS.dailysalessummary.controller;

import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummary;
import com.java.Zhimdhen_POS.dailysalessummary.repository.DailySalesSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales-summary")
@CrossOrigin
public class DailySalesSummaryController {

    @Autowired
    private DailySalesSummaryRepository repository;

    @GetMapping
    public List<DailySalesSummary> getSalesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return repository.findBySaleDate(date);
    }
}
