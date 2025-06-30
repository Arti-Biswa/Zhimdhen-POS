package com.java.Zhimdhen_POS.table.controller;

import com.java.Zhimdhen_POS.table.dto.TableDto;
import com.java.Zhimdhen_POS.table.mapper.TableMapper;
import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTable(@PathVariable Long id) {
        boolean deleted = tableService.deleteTableById(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("status", true, "message", "Table deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", false, "message", "Table not found"));
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CASHIER')")
    @GetMapping("/list")
    public List<TableEntity> getAllTables() {
        return tableService.getAllTables();
    }
}
