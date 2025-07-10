package com.java.Zhimdhen_POS.dailysalessummary.service;

import com.java.Zhimdhen_POS.dailysalessummary.mapper.DailySalesSummaryMapper;
import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummary;
import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummaryDTO;
import com.java.Zhimdhen_POS.dailysalessummary.repository.DailySalesSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailySalesSummaryService {

    private final DailySalesSummaryRepository repository;
    private final DailySalesSummaryMapper mapper;

    public DailySalesSummaryService(DailySalesSummaryRepository repository, DailySalesSummaryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void saveAll(List<DailySalesSummaryDTO> dtoList) {
        List<DailySalesSummary> entityList = dtoList.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        repository.saveAll(entityList);
    }
}