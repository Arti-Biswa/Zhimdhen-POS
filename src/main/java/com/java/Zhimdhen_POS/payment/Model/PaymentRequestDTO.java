package com.java.Zhimdhen_POS.payment.Model;

import java.util.List;

public class PaymentRequestDTO {
    private Long orderId;
    private String paymentMethod;
    private double amount;
    private String transferType;
    private String journalNo;
    private String remarks;

    private List<PaymentItemDTO> items; // Add this

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getJournalNo() {
        return journalNo;
    }

    public void setJournalNo(String journalNo) {
        this.journalNo = journalNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<PaymentItemDTO> getItems() {
        return items;
    }

    public void setItems(List<PaymentItemDTO> items) {
        this.items = items;
    }
}
