package com.java.Zhimdhen_POS.table.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class TableDto {
    private long id;
    private String tableNumber;
    private Long restaurantId; // optional, depending on what you need

}
