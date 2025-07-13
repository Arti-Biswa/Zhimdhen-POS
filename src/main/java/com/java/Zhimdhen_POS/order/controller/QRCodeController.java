package com.java.Zhimdhen_POS.order.controller;


import com.google.zxing.WriterException;
import com.java.Zhimdhen_POS.order.service.QRCodeService;
import com.java.Zhimdhen_POS.table.model.TableEntity;
import com.java.Zhimdhen_POS.table.repository.TableRepository;
import com.java.Zhimdhen_POS.table.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/qr")
@CrossOrigin
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @Autowired
    private TableService tableService;

    @Autowired
    private TableRepository tableRepository;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @CrossOrigin(origins = "http://1192.168.132.1:4200")

    @GetMapping(value = "/table", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(
            @RequestParam String tableNumber,
            @RequestParam Long restaurantId
    ) {
        try {
            TableEntity table = tableRepository.findByTableNumberAndRestaurantId(tableNumber, restaurantId)
                    .orElseThrow(() -> new RuntimeException("Table not found"));

            String qrText = "http://192.168.132.1:4200/menu"
                    + "?restaurantId=" + restaurantId
                    + "&tableId=" + table.getId(); // ✅ correct table ID now

            byte[] qrImage = qrCodeService.generateQRCode(qrText, 300, 300);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
