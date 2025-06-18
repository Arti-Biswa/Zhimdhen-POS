package com.java.Zhimdhen_POS.table.controller;

import com.java.Zhimdhen_POS.table.dto.TableDto;
import com.java.Zhimdhen_POS.table.mapper.TableMapper;
import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {
    @Autowired
    private TableService tableService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public TableEntity addTable(@RequestBody TableDto tableDto) {
        TableEntity table = TableMapper.toEntity(tableDto);
        return tableService.addTable(table);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CASHIER')")
    @GetMapping("/list")
    public List<TableEntity> getAllTables() {
        return tableService.getAllTables();
    }
}
