package com.java.Zhimdhen_POS.dailysalessummary.mapper;

import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummary;
import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class DailySalesSummaryMapper {
    public DailySalesSummary toEntity(DailySalesSummaryDTO dto) {
        DailySalesSummary entity = new DailySalesSummary();
        entity.setSaleDate(dto.getSaleDate());
        entity.setItemName(dto.getItemName());
        entity.setQuantity(dto.getQuantitySold());
        entity.setTotalAmount(dto.getTotalAmount());
        return entity;
    }

    public DailySalesSummaryDTO toDTO(DailySalesSummary entity) {
        DailySalesSummaryDTO dto = new DailySalesSummaryDTO();
        dto.setSaleDate(entity.getSaleDate());
        dto.setItemName(entity.getItemName());
        dto.setQuantitySold(entity.getQuantity());
        dto.setTotalAmount(entity.getTotalAmount());
        return dto;
    }
}
