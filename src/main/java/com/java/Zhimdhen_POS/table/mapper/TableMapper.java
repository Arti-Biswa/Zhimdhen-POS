package com.java.Zhimdhen_POS.table.mapper;

import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.dto.TableDto; // ✅ This import was missing

public class TableMapper {
    public static TableEntity toEntity(TableDto dto) {
        return new TableEntity(dto.getTableNumber());
    }
}
