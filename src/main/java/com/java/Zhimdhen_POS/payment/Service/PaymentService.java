package com.java.Zhimdhen_POS.payment.Service;

import com.java.Zhimdhen_POS.dailysalessummary.model.DailySalesSummaryDTO;
import com.java.Zhimdhen_POS.dailysalessummary.service.DailySalesSummaryService;
import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.repository.OrderRepository;
import com.java.Zhimdhen_POS.order.model.OrderItem;
import com.java.Zhimdhen_POS.payment.Model.Payment;
import com.java.Zhimdhen_POS.payment.Model.PaymentRequestDTO;
import com.java.Zhimdhen_POS.payment.Repository.PaymentRepository;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DailySalesSummaryService dailySalesSummaryService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Payment savePaymentAndSummary(PaymentRequestDTO dto) {
        // ✅ Save payment
        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setAmount(dto.getAmount());
        payment.setTransferType(dto.getTransferType());
        payment.setJournalNo(dto.getJournalNo());
        payment.setRemarks(dto.getRemarks());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // ✅ Fetch order and items
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<DailySalesSummaryDTO> summaryList = order.getItems().stream()
                .map(item -> createSalesSummary(item))
                .collect(Collectors.toList());

        // ✅ Save daily summaries
        dailySalesSummaryService.saveAll(summaryList);

        return savedPayment;
    }

    private DailySalesSummaryDTO createSalesSummary(OrderItem item) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + item.getProductId()));

        double total = product.getPrice() * item.getQuantity();

        return DailySalesSummaryDTO.builder()
                .saleDate(LocalDate.now())
                .itemName(product.getName())
                .quantitySold(item.getQuantity())
                .totalAmount(total)
                .build();
    }
}
