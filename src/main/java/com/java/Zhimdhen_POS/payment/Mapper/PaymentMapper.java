package com.java.Zhimdhen_POS.payment.Mapper;

import com.java.Zhimdhen_POS.payment.Model.Payment;
import com.java.Zhimdhen_POS.payment.Model.PaymentRequestDTO;

import java.time.LocalDateTime;

public class PaymentMapper {
    public Payment toEntity(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setAmount(dto.getAmount());
        payment.setTransferType(dto.getTransferType());
        payment.setJournalNo(dto.getJournalNo());
        payment.setRemarks(dto.getRemarks());
        payment.setPaymentDate(LocalDateTime.now());
        return payment;
    }
}
