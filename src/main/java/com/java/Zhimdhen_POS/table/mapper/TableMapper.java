package com.java.Zhimdhen_POS.table.mapper;

import com.java.Zhimdhen_POS.table.model.TableDto;
import com.java.Zhimdhen_POS.table.model.TableEntity;

public class TableMapper {
    public static TableEntity toEntity(TableDto dto) {
        TableEntity t = new TableEntity();        // no‑arg ctor
        t.setTableNumber(dto.getTableNumber());
        return t;
    }

    public static TableDto toDto(TableEntity entity) {
        TableDto dto = new TableDto();
        dto.setId(entity.getId());
        dto.setTableNumber(entity.getTableNumber());
        dto.setRestaurantId(entity.getRestaurant().getId());
        return dto;
    }
}
