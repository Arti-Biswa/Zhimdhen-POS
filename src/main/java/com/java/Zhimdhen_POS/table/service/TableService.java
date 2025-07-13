package com.java.Zhimdhen_POS.table.service;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.table.mapper.TableMapper;
import com.java.Zhimdhen_POS.table.model.TableDto;
import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.repository.TableRepository;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TableEntity addTable(TableDto dto) {
        if (dto.getTableNumber() == null || dto.getTableNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Table number must not be null or empty");
        }
        /* 1️⃣  Logged‑in admin → restaurant */
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();          // token subject = email
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Admin not found"));

        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant must be set on the table");
        }

        // 🔁 Check for duplicates in this restaurant
        if (tableRepository.existsByRestaurantAndTableNumber(restaurant, dto.getTableNumber())) {
            throw new RuntimeException("Table '" + dto.getTableNumber() + "' already exists!");
        }

        TableEntity table = TableMapper.toEntity(dto);
        table.setRestaurant(admin.getRestaurant());   // never null
        return tableRepository.save(table);

    }

    public List<TableEntity> getAllTables() {
        return tableRepository.findAll();
    }

    public boolean deleteTableById(Long id) {
        if (tableRepository.existsById(id)) {
            tableRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TableDto> findTablesByAdminRestaurant(Long restaurantId) {
        return tableRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(TableMapper::toDto)
                .toList();
    }

    public TableEntity getById(Long tableId) {
        return tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found with id: " + tableId));
    }

    public TableEntity getByIdAndRestaurantId(String tableNumber, Long restaurantId) {
        return tableRepository.findByTableNumberAndRestaurantId(tableNumber, restaurantId)
                .orElseThrow(() -> new RuntimeException("Table not found for given restaurant and table ID"));
    }


}
