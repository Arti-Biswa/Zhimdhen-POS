package com.java.Zhimdhen_POS.table.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class TableDto {
    private String tableNumber;

    public TableDto() {}

    public TableDto(String tableNumber) {
        this.tableNumber = tableNumber;
    }

}
