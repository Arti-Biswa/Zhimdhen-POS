package com.java.Zhimdhen_POS.table.service;

import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    /**
     * Add a new table after checking for duplicate table number.
     * @param table The table entity to save.
     * @return The saved table entity.
     */
    public TableEntity addTable(TableEntity table) {
        if (table.getTableNumber() == null || table.getTableNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Table number must not be null or empty");
        }

        if (tableRepository.existsByTableNumber(table.getTableNumber())) {
            throw new RuntimeException("Table with number '" + table.getTableNumber() + "' already exists!");
        }

        return tableRepository.save(table);
    }

    /**
     * Retrieve all tables from the database.
     * @return List of table entities.
     */
    public List<TableEntity> getAllTables() {
        return tableRepository.findAll();
    }
}
