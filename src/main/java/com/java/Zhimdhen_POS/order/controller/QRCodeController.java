package com.java.Zhimdhen_POS.order.controller;


import com.google.zxing.WriterException;
import com.java.Zhimdhen_POS.order.service.QRCodeService;
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

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @CrossOrigin(origins =  "http://192.168.1.108:4200") // ⚠️ For development only. Replace with specific IP for production.
    @GetMapping(value = "/table/{tableId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String tableId) {
        try {
            // You can make the host dynamic if needed, or change to a fixed IP for QR redirect
            String baseUrl = "http://192.168.1.108:4200/menu?table=";
            String qrText = baseUrl + URLEncoder.encode(tableId, StandardCharsets.UTF_8);


            byte[] qrImage = qrCodeService.generateQRCode(qrText, 300, 300);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);

            return ResponseEntity.ok().headers(headers).body(qrImage);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

}
