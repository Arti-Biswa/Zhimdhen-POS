package com.java.Zhimdhen_POS.payment.Repository;

import com.java.Zhimdhen_POS.payment.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
