package com.kiran.major.service;

import com.kiran.major.model.Payment;
import com.kiran.major.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public boolean createPayment(Map<String, String> paymentDetails) {

        try {
            Payment payment = new Payment();
            payment.setPaymentReferenceId(paymentDetails.get("paymentId"));
            payment.setRazorPayOrderId(paymentDetails.get("orderId"));
            double price = Double.parseDouble(paymentDetails.get("paymentAmount")) / 100.0;
            payment.setPaymentAmount(price);

            // Save the payment details to the repository
            paymentRepository.save(payment);

            return true;
        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Error saving payment: " + e.getMessage());

            return false;
        }
    }
}
