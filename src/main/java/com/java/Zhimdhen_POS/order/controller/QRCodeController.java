package com.java.Zhimdhen_POS.order.controller;


import com.google.zxing.WriterException;
import com.java.Zhimdhen_POS.order.service.QRCodeService;
import com.java.Zhimdhen_POS.table.model.TableEntity;
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

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @CrossOrigin(origins = "http://192.168.1.115:4200")
    // ⚠️ For development only. Replace with specific IP for production.
    @GetMapping(value = "/table/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String tableId) {
        try {
            // 1) look up the table to get its restaurantId
            TableEntity table = tableService.getById(Long.valueOf(tableId));         // inject TableService
            Long restaurantId = table.getRestaurant().getId();

            // 2) build the URL consumable by Angular
            String qrText = "http://192.168.1.115:4200/menu"
                    + "?restaurantId=" + restaurantId
                    + "&tableId=" + URLEncoder.encode(tableId, StandardCharsets.UTF_8);

            byte[] qrImage = qrCodeService.generateQRCode(qrText, 300, 300);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrImage);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
