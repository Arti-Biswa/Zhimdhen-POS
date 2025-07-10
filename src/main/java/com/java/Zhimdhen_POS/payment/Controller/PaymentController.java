package com.java.Zhimdhen_POS.payment.Controller;

import com.java.Zhimdhen_POS.payment.Model.Payment;
import com.java.Zhimdhen_POS.payment.Model.PaymentRequestDTO;
import com.java.Zhimdhen_POS.payment.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/submit")
    public ResponseEntity<Payment> submitPayment(@RequestBody PaymentRequestDTO dto) {
        Payment savedPayment = paymentService.savePaymentAndSummary(dto);
        return ResponseEntity.ok(savedPayment);
    }
}
